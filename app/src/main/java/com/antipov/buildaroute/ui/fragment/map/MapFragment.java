package com.antipov.buildaroute.ui.fragment.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.common.Const;
import com.antipov.buildaroute.data.pojo.autocomplete.WayPoint;
import com.antipov.buildaroute.ui.adapter.WaypointsListAdapter;
import com.antipov.buildaroute.ui.base.BaseFragment;
import com.antipov.buildaroute.ui.dialog.AddressDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.antipov.buildaroute.common.Const.Requests.REQUEST_GET_ADDRESS;
import static com.antipov.buildaroute.common.Const.Requests.REQUEST_GET_FINISH;
import static com.antipov.buildaroute.common.Const.Requests.REQUEST_GET_START;

public class MapFragment extends BaseFragment implements com.antipov.buildaroute.ui.fragment.map.MapView,
        OnMapReadyCallback {

    @Inject
    MapPresenter<com.antipov.buildaroute.ui.fragment.map.MapView, MapInteractor> presenter;

    @BindView(R.id.map) MapView map;
    @BindView(R.id.tv_start_point) TextView startPoint;
    @BindView(R.id.tv_end_point) TextView endPoint;
    @BindView(R.id.btn_add_between) Button addInBetween;
    @BindView(R.id.btn_start) Button startDriving;
    @BindView(R.id.points_recycler) RecyclerView waypoints;

    private final int ROUTE_MAP_PADDING = 100;
    private final String DIALOG_TAG = "address-dialog";
    private GoogleMap googleMap;
    private WayPoint start;
    private WayPoint finish;
    private WaypointsListAdapter adapter;
    private Polyline route;
    private Marker startMarker;
    private Marker finishMarker;

    @Override
    public void onStart() {
        super.onStart();
        // according to map lifecycle
        map.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAppComponent().inject(this);
        presenter.attachView(this);
        // according to map lifecycle
        map.onCreate(savedInstanceState);
        // requesting map
        map.getMapAsync(this);
        // creating adapter
        initAdapter();
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseActivity());
        adapter = new WaypointsListAdapter();
        waypoints.setLayoutManager(layoutManager);
        waypoints.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // according to map lifecycle
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // according to map lifecycle
        map.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        // according to map lifecycle
        map.onStop();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, getInflatedView());
    }

    @Override
    public void initListeners() {
        // for picking start point
        startPoint.setOnClickListener(l -> presenter.addStartOrFinish(REQUEST_GET_START));
        // for picking finish point
        endPoint.setOnClickListener(l -> presenter.addStartOrFinish(REQUEST_GET_FINISH));
        // for picking in-between point
        addInBetween.setOnClickListener(l -> presenter.addWayPoint(REQUEST_GET_ADDRESS));
        // for start driving
        startDriving.setOnClickListener(l -> presenter.startDriving());
    }

    /**
     * Method for starting for result address picker dialog
     * @see AddressDialog
     *
     * take as parameter request codes:
     * @see Const.Requests#REQUEST_GET_START
     * @see Const.Requests#REQUEST_GET_FINISH
     * @see Const.Requests#REQUEST_GET_FINISH
     *
     * @param request request code
     */
    @Override
    public void showAddressDialog(int request) {
        FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = new AddressDialog();
        dialogFragment.setTargetFragment(this, request);
        dialogFragment.show(ft, DIALOG_TAG);
    }

    /**
     * when address was picked
     *
     * @param requestCode - request code
     *                    @see Const.Requests#REQUEST_GET_FINISH
     * @param resultCode - result code
     *                   @see android.app.Activity#RESULT_OK
     * @param data - intent with selected address
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            WayPoint item = data.getParcelableExtra(Const.Args.SELECTED_ADDRESS);
            switch (requestCode) {
                case REQUEST_GET_START:
                    start = item;
                    break;
                case REQUEST_GET_FINISH:
                    finish = item;
                    break;
                case REQUEST_GET_ADDRESS:
                    insertWayPoint(item);
            }
            presenter.onAddressSelected(item, requestCode);
        }
    }

    /**
     * method for adding marker to map
     *  @param lat - lat
     * @param lng - lng
     * @param requestCode
     */
    @Override
    public void addMarker(float lat, float lng, int requestCode) {
        if (googleMap != null) {
            // placing marker in the map
            LatLng coordinates = new LatLng(lat, lng);
            Marker marker = googleMap.addMarker(new MarkerOptions().position(coordinates));

            // animating camera
            CameraPosition cameraPosition =
                    new CameraPosition
                    .Builder()
                    .target(coordinates)
                    .zoom(12)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            switch (requestCode){
                case REQUEST_GET_START:
                    startMarker = marker;
                    break;
                case REQUEST_GET_FINISH:
                    finishMarker = marker;
                    break;
            }
        }
    }

    /**
     * remove old start marker
     */
    @Override
    public void removeOldStart() {
        if (startMarker != null) startMarker.remove();
    }

    /**
     * remove old finish marker
     */
    @Override
    public void removeOldFinish() {
        if (finishMarker != null) finishMarker.remove();
    }

    /**
     * @return waypoints count
     */
    @Override
    public int getWaypointsCount() {
        return adapter.getItemCount();
    }


    /**
     * adds new item to waypoints list
     *
     * @param item new item
     */
    @Override
    public void insertWayPoint(WayPoint item) {
        adapter.add(item);
    }

    /**
     * calles when reached waypoints limit
     */
    @Override
    public void notifyWaypointsLimit() {
        onError(R.string.waypoints_limit_reached);
    }

    /**
     * @return waypoint of the start
     */
    @Override
    @Nullable
    public WayPoint getStartPoint() {
        return start;
    }

    /**
     * @return waypoint of the finish
     */
    @Override
    @Nullable
    public WayPoint getFinishPoint() {
        return finish;
    }

    /**
     * calls if start is not selected
     */
    @Override
    public void notifyNullStart() {
        onError(R.string.choose_start);
    }

    /**
     * calls if finish is not selected
     */
    @Override
    public void notifyNullFinish() {
        onError(R.string.choose_end);
    }

    /**
     * @return waypoints from adapter
     */
    @Override
    public List<WayPoint> getWaypoints() {
        return adapter.getData();
    }

    /**
     * @param formattedAddress - text for start view
     */
    @Override
    public void updateStartText(String formattedAddress) {
        startPoint.setText(formattedAddress);
    }

    /**
     * @param formattedAddress - text for finish view
     */
    @Override
    public void updateFinishText(String formattedAddress) {
        endPoint.setText(formattedAddress);
    }

    /**
     * clearing map from old polylines
     */
    @Override
    public void removeOldPolyline() {
        if (route != null) route.remove();
    }

    /**
     * adds new polyline
     * @param coordinates - encoded route coordinates
     */
    @Override
    public void createNewPolyline(String coordinates) {
        // encoding string polyline
        List<LatLng> encoded = PolyUtil.decode(coordinates);

        // creating polyline
        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.addAll(encoded);
        lineOptions.width(5);
        lineOptions.color(ContextCompat.getColor(Objects.requireNonNull(getBaseActivity()), R.color.colorAccent));

        // calculating polyline bounds
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (LatLng coordinate : encoded) {
            builder.include(coordinate);
        }

        // zooming camera to show route
        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(), ROUTE_MAP_PADDING);

        if (googleMap != null) {
            googleMap.animateCamera(cu);
            route = googleMap.addPolyline(lineOptions);
        }
    }

    @Override
    public void showLoadingFullscreen() {

    }

    @Override
    public void hideLoadingFullscreen() {

    }

    /**
     * saving reference to the map when it`s ready
     *
     * @param googleMap - map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // according to map lifecycle
        map.onDestroy();
        presenter.detachView();
    }
}
