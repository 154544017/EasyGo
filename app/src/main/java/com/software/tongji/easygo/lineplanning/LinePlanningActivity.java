package com.software.tongji.easygo.lineplanning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Schedule;
import com.software.tongji.easygo.bean.ScheduleLab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LinePlanningActivity extends AppCompatActivity implements AMapNaviListener{

    private static final String EXTRA_TOUR_ID = "com.software.tongji.easygo.extra_id";

    @BindView(R.id.mapview)
    MapView mMapView;
    private AMap mAMap;

    private ArrayList<NaviLatLng> mStartPoints = new ArrayList<>();
    private ArrayList<NaviLatLng> mWayPoints = new ArrayList<>();
    private ArrayList<NaviLatLng> mEndPoints = new ArrayList<>();

    private RouteOverLay mRouteOverLay;
    private AMapNavi aMapNavi;

    private String mTourId;

    public static Intent newIntent(Context packageContext, String tourId){
        Intent intent = new Intent(packageContext, LinePlanningActivity.class);
        intent.putExtra(EXTRA_TOUR_ID, tourId);
        return intent;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTourId = getIntent().getStringExtra(EXTRA_TOUR_ID);
        setPoints();

        aMapNavi = AMapNavi.getInstance(this);
        aMapNavi.addAMapNaviListener(this);
        aMapNavi.setEmulatorNaviSpeed(150);

        setContentView(R.layout.activity_line_planning);
        initView(savedInstanceState);
    }

    // 初始化View
    private void initView(Bundle savedInstanceState) {

        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mStartPoints.get(0).getLatitude(),
                mStartPoints.get(0).getLongitude()),16));
        mRouteOverLay = new RouteOverLay(mAMap, null, getApplicationContext());
    }

    private void setPoints(){
        List<Schedule> scheduleList = ScheduleLab.get(this).getScheduleList(mTourId);
        Schedule schedule = scheduleList.get(0);
        mStartPoints.add(new NaviLatLng(schedule.getLatPoint(),schedule.getLonPoint()));
        schedule = scheduleList.get(scheduleList.size() - 1);
        mEndPoints.add(new NaviLatLng(schedule.getLatPoint(),schedule.getLonPoint()));
        for(int i = 1; i < scheduleList.size() - 1; i++){
            schedule = scheduleList.get(i);
            mWayPoints.add(new NaviLatLng(schedule.getLatPoint(),schedule.getLonPoint()));
        }
    }

    private void calculateDriveRoute() {

        boolean isSuccess = aMapNavi.calculateDriveRoute(mStartPoints,
                mEndPoints, mWayPoints, PathPlanningStrategy.DRIVING_DEFAULT);
        if (!isSuccess) {
            Toast.makeText(this, "路线计算失败,检查参数情况", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onArrivedWayPoint(int arg0) {

    }

    @Override
    public void onCalculateRouteFailure(int arg0) {
        Toast.makeText(this, "路径规划出错" + arg0, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        AMapNaviPath naviPath = aMapNavi.getNaviPath();
        if (naviPath == null) {
            return;
        }
        mRouteOverLay.setAMapNaviPath(naviPath);
        mRouteOverLay.addToMap();
    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onGetNavigationText(int arg0, String arg1) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onGpsOpenStatus(boolean arg0) {

    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {
        calculateDriveRoute();
    }

    @Override
    public void onLocationChange(AMapNaviLocation arg0) {
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo arg0) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onStartNavi(int arg0) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

//------------------生命周期重写函数---------------------------

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        aMapNavi.destroy();
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo arg0) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }
}
