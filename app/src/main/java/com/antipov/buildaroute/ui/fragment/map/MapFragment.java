package com.antipov.buildaroute.ui.fragment.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.common.Const;
import com.antipov.buildaroute.data.pojo.WayPoint;
import com.antipov.buildaroute.ui.adapter.WaypointsListAdapter;
import com.antipov.buildaroute.ui.base.BaseFragment;
import com.antipov.buildaroute.ui.dialog.AddressDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class MapFragment extends BaseFragment implements com.antipov.buildaroute.ui.fragment.map.MapView,
        OnMapReadyCallback {

    @Inject
    MapPresenter<com.antipov.buildaroute.ui.fragment.map.MapView, MapInteractor> presenter;

    @BindView(R.id.map) MapView map;
    @BindView(R.id.tv_start_point) TextView startPoint;
    @BindView(R.id.tv_end_point) TextView endPoint;
    @BindView(R.id.btn_add_between) Button addInBetween;
    @BindView(R.id.points_recycler) RecyclerView waypoints;

    private final String DIALOG_TAG = "address-dialog";
    private final int REQUEST_GET_START = 1;
    private final int REQUEST_GET_FINISH = 2;
    private final int REQUEST_GET_ADDRESS = 8;
    private GoogleMap googleMap;
    private WayPoint start;
    private WayPoint finish;
    private WaypointsListAdapter adapter;

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
    }

    /**
     * Method for starting for result address picker dialog
     * @see AddressDialog
     *
     * take as parameter request codes:
     * @see #REQUEST_GET_ADDRESS
     * @see #REQUEST_GET_FINISH
     * @see #REQUEST_GET_FINISH
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
     *                    @see #REQUEST_GET_FINISH
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
            presenter.onAddressSelected(item);
        }
    }

    /**
     * method for adding marker to map
     *
     * @param lat - lat
     * @param lng - lng
     */
    @Override
    public void addMarker(float lat, float lng) {
        if (googleMap != null) {
            LatLng marker = new LatLng(lat, lng);
            googleMap.addMarker(new MarkerOptions().position(marker));
            CameraPosition cameraPosition =
                    new CameraPosition
                    .Builder()
                    .target(marker)
                    .zoom(12)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
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

//        String polyline = "kvkmElvvnU|@Tx@v@\\`ABjAF|Zn@?Cc\\q@{Aw@}@eDkAaD]}ADcJlB{Bp@yAd@aAQyDmA_AW_CBuAh@gJ~HeCxB}@nAmI~HoIbJgPjQk@h@aDnF{C|I{BvMwB|NyAvFaMfYe`@h|@cGjKuFnLoTfj@_I`RiItRSBKDu@vAeCtDeMzMyCzC}DbFsApDi@pDE~D\\zD~AjFdCzHb@h@b@hC`@fDTrGNve@HtYLxpAFrv@BnTYzKu@`IoAtH}HlXsGbTmIjXwLf`@_BzDyFbLeSz\\s[pi@uG`LwBrE_DzKuApJ]hH?rZB~u@Hbs@DjLMlCCLE|DGtDS~Hw@zGsClM}BjHmB`DoAdAgBx@mD|@EFOP}BRwIj@eFNiSf@w[n@wz@hBgMJkd@q@yXa@}QY}[g@kGYgHq@mFu@oMkCgLoD_JqDqAw@c@c@yFuCeOiI}FmCgAk@eBuA{DoG_Ai@gAQeAHeBjAkF|FuBxAGTaIrEgIvEy\\|RyDlCiDfDyErGkIjMeO…LcVbu@YvBcBvGqDpKkKvZ}G~QuBdEyExGgDzC{C|BeBz@yFlAyE?kGe@{GVcG]qKkAqH?cIJyEb@uGxAmEbBkKlEkBvAw@fAyA`Ei@nHqDlb@c@xHQpEDlC@rEq@dEaFfKgIlOkFjJwHrKkGvFqKvIcEfEyEjHyJnOiC|F{AxE{H~ZoBxHeD|KyIvWkFrOcDhJuArFqGx\\yB`NqDtWaC`RwAbGuBlFyC~G_GvOuG|PiJlPsL|QiDhDsCrBgKtFqE`C_C`BiCpCoAnBcI`OqF|H_CzBsE~CqUfLkChB}BfCyBvDiBtGc@rDKxDWlLs@jEwAjEeC|EqLzU}IrPaCrCwAlAiDjBqAf@aMvCgIjBiDl@_E|@iCx@{EvB{HnF{CrCkEjFeHzIcCbCkFdD{DrCoGrGkDdDM?iC|ByB~AaHjEo@p@sAtBu@hAg@^e@b@{@pBc@vAD~@TfCOfBkAlH{@vDw@jDSEWGWAi@HsCl@kAt@iAFYMOo@Qw@^OJAlBXRA";
//
//        List<LatLng> polyz = PolyUtil.decode(polyline);
//
//        if (polyz != null) {
//            PolylineOptions lineOptions = new PolylineOptions();
//            lineOptions.addAll(polyz);
//            lineOptions.width(5);
//            lineOptions.color(ContextCompat.getColor(getActivity(), R.color.colorAccent));
//            googleMap.addPolyline(lineOptions);
//        }

//
//        // For dropping a marker at a point on the Map
//        LatLng sydney = new LatLng(-34, 151);
//        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
//
//        // For zooming automatically to the location of the marker
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // according to map lifecycle
        map.onDestroy();
        presenter.detachView();
    }
}
