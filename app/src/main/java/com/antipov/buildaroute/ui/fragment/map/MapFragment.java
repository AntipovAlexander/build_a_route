package com.antipov.buildaroute.ui.fragment.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.common.Const;
import com.antipov.buildaroute.data.pojo.AutocompleteItem;
import com.antipov.buildaroute.ui.base.BaseFragment;
import com.antipov.buildaroute.ui.dialog.AddressDialog;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;
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

    private final String DIALOG_TAG = "address-dialog";
    private final int REQUEST_GET_START = 1;
    private final int REQUEST_GET_FINISH = 2;
    private final int REQUEST_GET_ADDRESS = 8;
    private GoogleMap googleMap;
    private AutocompleteItem start;
    private AutocompleteItem finish;

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
        startPoint.setOnClickListener(l -> presenter.onMapButtonClick(REQUEST_GET_START));
        endPoint.setOnClickListener(l -> presenter.onMapButtonClick(REQUEST_GET_FINISH));
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GET_START:
                if (resultCode == RESULT_OK) {
                    start = data.getParcelableExtra(Const.Args.SELECTED_ADDRESS);
                }
                break;
            case REQUEST_GET_FINISH:
                if (resultCode == RESULT_OK) {
                    finish = data.getParcelableExtra(Const.Args.SELECTED_ADDRESS);
                }
                break;
        }
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
