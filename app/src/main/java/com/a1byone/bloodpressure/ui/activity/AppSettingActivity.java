package com.a1byone.bloodpressure.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.utils.ToastUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 设置
 */
public class AppSettingActivity extends AppCompatActivity {
    private final static String TAG = AppSettingActivity.class.getSimpleName();

    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;

    private CommonTitleBar appSettingToolBar;
    private Switch switchSyncGoogleFit;
    OnDataPointListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        FitnessOptions fitnessOptions =
                FitnessOptions.builder().addDataType(DataType.TYPE_ACTIVITY_SAMPLES).build();
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_OAUTH_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);
        } else {
            subscribe();
        }
        mListener = new OnDataPointListener() {
                    @Override
                    public void onDataPoint(DataPoint dataPoint) {
                        for (Field field : dataPoint.getDataType().getFields()) {
                            Value val = dataPoint.getValue(field);
                            Log.i(TAG, "Detected DataPoint field: " + field.getName());
                            Log.i(TAG, "Detected DataPoint value: " + val);
                        }
                    }
                };
        initView();
    }

    public void subscribe() {
        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .findDataSources(new DataSourcesRequest.Builder()
                                .setDataTypes(DataType.TYPE_LOCATION_SAMPLE)
                                .setDataSourceTypes(DataSource.TYPE_RAW)
                                .build())
                .addOnSuccessListener(new OnSuccessListener<List<DataSource>>() {
                            @Override
                            public void onSuccess(List<DataSource> dataSources) {
                                for (DataSource dataSource : dataSources) {
                                    Log.i(TAG, "Data source found: " + dataSource.toString());
                                    Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());

                                    // Let's register a listener to receive Activity data!
                                    if (dataSource.getDataType().equals(DataType.TYPE_LOCATION_SAMPLE)
                                            && mListener == null) {
                                        Log.i(TAG, "Data source for LOCATION_SAMPLE found!  Registering.");
                                        registerFitnessDataListener(dataSource, DataType.TYPE_LOCATION_SAMPLE);
                                    }
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "failed", e);
                            }
                        });
    }

    private void registerFitnessDataListener(DataSource dataSource, DataType typeLocationSample) {
    }

    private void initView() {
        appSettingToolBar = findViewById(R.id.app_setting_title_bar);
        switchSyncGoogleFit = findViewById(R.id.switch_sync_google_fit);

        appSettingToolBar.getLeftCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        switchSyncGoogleFit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    ToastUtil.showShort(AppSettingActivity.this, "Sync Google Fit");
                    SyncGoogleFit();
                } else {
                    CancelSyncGoogleFit();
                }
            }
        });
    }

    private void SyncGoogleFit() {
        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .add(new SensorRequest.Builder()
                                //.setDataSource(DataSource.TYPE_RAW) // Optional but recommended for custom data sets.
                                .setDataType(DataType.TYPE_WEIGHT) // Can't be omitted.
                                .setSamplingRate(10, TimeUnit.SECONDS)
                                .build(),
                        mListener)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "Listener registered!");
                                } else {
                                    Log.e(TAG, "Listener not registered.", task.getException());
                                }
                            }
                        });
    }

    private void CancelSyncGoogleFit() {
        Fitness.getSensorsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .remove(mListener)
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                            @Override
                            public void onComplete(@NonNull Task<Boolean> task) {
                                if (task.isSuccessful() && task.getResult()) {
                                    Log.i(TAG, "Listener was removed!");
                                } else {
                                    Log.i(TAG, "Listener was not removed.");
                                }
                            }
                        });
    }
}
