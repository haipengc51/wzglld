package com.jiekai.wzglld.ui.record;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglld.R;
import com.jiekai.wzglld.config.Config;
import com.jiekai.wzglld.config.IntentFlag;
import com.jiekai.wzglld.config.SqlUrl;
import com.jiekai.wzglld.entity.DevicedocEntity;
import com.jiekai.wzglld.entity.DevicestoreEntity;
import com.jiekai.wzglld.entity.UserNameEntity;
import com.jiekai.wzglld.ui.base.MyBaseActivity;
import com.jiekai.wzglld.utils.CommonUtils;
import com.jiekai.wzglld.utils.GlidUtils;
import com.jiekai.wzglld.utils.PictureSelectUtils;
import com.jiekai.wzglld.utils.StringUtils;
import com.jiekai.wzglld.utils.TimeUtils;
import com.jiekai.wzglld.utils.dbutils.DBManager;
import com.jiekai.wzglld.utils.dbutils.DbCallBack;
import com.jiekai.wzglld.utils.dbutils.DbDeal;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LaoWu on 2018/1/15.
 */

public class RecordDeviceRepairDetailActivity extends MyBaseActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.record_type)
    TextView recordType;
    @BindView(R.id.repair_type)
    TextView repairType;
    @BindView(R.id.device_id)
    TextView deviceId;
    @BindView(R.id.read_card)
    TextView readCard;
    @BindView(R.id.record_image)
    ImageView recordImage;
    @BindView(R.id.duihao)
    TextView duihao;
    @BindView(R.id.jinghao)
    TextView jinghao;
    @BindView(R.id.operator_people)
    TextView operatorPeople;
    @BindView(R.id.operator_time)
    TextView operatorTime;
    @BindView(R.id.check_people)
    TextView checkPeople;
    @BindView(R.id.check_time)
    TextView checkTime;
    @BindView(R.id.check_result)
    TextView checkResult;
    @BindView(R.id.check_remark)
    TextView checkRemark;
    @BindView(R.id.operator_remark)
    TextView operatorRemark;

    private DevicestoreEntity currentData;
    private List<LocalMedia> choosePictures = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_device_repair_detail);
    }

    @Override
    public void initData() {
        back.setVisibility(View.VISIBLE);
        title.setText(getResources().getString(R.string.record_repair));

        back.setOnClickListener(this);
        recordImage.setOnClickListener(this);

        currentData = (DevicestoreEntity) getIntent().getSerializableExtra(IntentFlag.DATA);
    }

    @Override
    public void initOperation() {
        if (currentData != null) {
            deviceId.setText(CommonUtils.getDataIfNull(currentData.getSBBH()));
            operatorPeople.setText(CommonUtils.getDataIfNull(currentData.getCzrname()));
            operatorTime.setText(TimeUtils.dateToStringYYYYmmdd(currentData.getCZSJ()));
            operatorRemark.setText(CommonUtils.getDataIfNull(currentData.getBZ()));
            if (currentData.getSHSJ() != null) {
                checkTime.setText(TimeUtils.dateToStringYYYYmmdd(currentData.getSHSJ()));
            }
            checkRemark.setText(CommonUtils.getDataIfNull(currentData.getSHBZ()));
            if (Config.repair_weixiu.equals(currentData.getLB())) {
                repairType.setText("维修");
            } else if (Config.repair_daxiu.equals(currentData.getLB())) {
                repairType.setText("大修");
            } else if (Config.repair_fanchang.equals(currentData.getLB())) {
                repairType.setText("返厂");
            }
            if ("1".equals(currentData.getSHYJ())) {
                checkResult.setText("通过");
            } else if ("0".equals(currentData.getSHYJ())) {
                checkResult.setText("未通过");
            } else {
                checkResult.setText("待审核");
            }

            showCommitImage(currentData.getID());
            getSHRName();
        } else {
            alert(R.string.no_data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.record_image:
                if (choosePictures != null && choosePictures.size() != 0) {
                    PictureSelectUtils.previewPicture(mActivity, choosePictures);
                }
                break;
        }
    }

    private void showCommitImage(int id) {
        if (id == -1) {
            alert(R.string.get_image_fail);
            return;
        }
        DbDeal dbdeal = DBManager.NewDbDeal(DBManager.SELECT);
        dbdeal.sql(SqlUrl.Get_Image_Path);
        if ("3".equals(currentData.getLB())) {
            dbdeal.params(new Object[]{id, Config.doc_sbwx});
        } else if ("4".equals(currentData.getLB())) {
            dbdeal.params(new Object[]{id, Config.doc_sbdx});
        } else if ("5".equals(currentData.getLB())) {
            dbdeal.params(new Object[]{id, Config.doc_sbfc});
        }
        dbdeal.clazz(DevicedocEntity.class);
        dbdeal.execut(new DbCallBack() {
            @Override
            public void onDbStart() {

            }

            @Override
            public void onError(String err) {

            }

            @Override
            public void onResponse(List result) {
                if (result != null && result.size() != 0) {
                    DevicedocEntity entity = (DevicedocEntity) result.get(0);
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(Config.WEB_HOLDE + entity.getWJDZ());
                    choosePictures.clear();
                    choosePictures.add(localMedia);
                    GlidUtils.displayImage(mActivity, Config.WEB_HOLDE + entity.getWJDZ(), recordImage);
                }
            }
        });
    }

    private void getSHRName() {
        if (StringUtils.isEmpty(currentData.getSHR())) {
            return;
        }
        DBManager.NewDbDeal(DBManager.SELECT)
                .sql(SqlUrl.GET_NAME_BY_ID)
                .params(new String[]{currentData.getSHR()})
                .clazz(UserNameEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {

                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            checkPeople.setText(((UserNameEntity) result.get(0)).getName());
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtils.clearPictureSelectorCache(mActivity);
    }
}
