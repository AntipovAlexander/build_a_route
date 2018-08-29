package com.antipov.buildaroute.ui.fragment.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.antipov.buildaroute.R;
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

public class MapFragment extends BaseFragment implements com.antipov.buildaroute.ui.fragment.map.MapView,
        OnMapReadyCallback {

    @Inject
    MapPresenter<com.antipov.buildaroute.ui.fragment.map.MapView, MapInteractor> presenter;

    @BindView(R.id.map) MapView map;
    @BindView(R.id.tv_start_point) TextView startPoint;
    @BindView(R.id.tv_end_point) TextView endPoint;

    private final String DIALOG_TAG = "address-dialog";
    private GoogleMap googleMap;

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
        map.getMapAsync(this);
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
        startPoint.setOnClickListener(l -> presenter.onMapButtonClick());
    }

    @Override
    public void showAddressDialog() {
        FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dialogFragment = new AddressDialog();
        dialogFragment.show(ft, DIALOG_TAG);
    }

    @Override
    public void showLoadingFullscreen() {

    }

    @Override
    public void hideLoadingFullscreen() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // according to map lifecycle
        map.onDestroy();
        presenter.detachView();
    }
}
