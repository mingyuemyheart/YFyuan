package com.moft.xfapply.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.task.SendReportAsyncTask;
import com.moft.xfapply.update.UpdateManager;
import com.moft.xfapply.utils.FileUtil;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.TelManagerUtil;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;
import com.moft.xfapply.widget.dialog.CustomAlertDialog.OnDoingListener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

@SuppressLint("SdCardPath")
public class FragmentProfile extends Fragment implements OnClickListener {
    private RelativeLayout rl_authorization;
    private RelativeLayout rl_machine;
    private RelativeLayout rl_about;
    private RelativeLayout rl_server;
    private RelativeLayout rl_myinfo;
    private RelativeLayout rl_pwdedit;
    private RelativeLayout rl_check;
    private RelativeLayout rl_cache;
    private RelativeLayout rl_login;
    private RelativeLayout rl_debugger;
    private RelativeLayout rl_offline_data_setting;

    private ImageView iv_avatar;
    private TextView tv_name;
    private ImageView iv_sex;
    private TextView tv_company;
    private TextView tv_check_status;
    
    private UpdateManager mManager = null;
    private UpdateManager.UpgradeVersionInfo upgradeVersionInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {        
        super.onActivityCreated(savedInstanceState);

        rl_myinfo = (RelativeLayout) getView().findViewById(R.id.rl_myinfo);
        rl_pwdedit = (RelativeLayout) getView().findViewById(R.id.rl_pwdedit);
        rl_authorization = (RelativeLayout) getView().findViewById(R.id.rl_authorization);
        rl_machine = (RelativeLayout) getView().findViewById(R.id.rl_machine);
        rl_about = (RelativeLayout) getView().findViewById(R.id.rl_about);
        rl_server = (RelativeLayout) getView().findViewById(R.id.rl_server);
        rl_check = (RelativeLayout) getView().findViewById(R.id.rl_check);
        rl_cache = (RelativeLayout) getView().findViewById(R.id.rl_cache);
        rl_login = (RelativeLayout) getView().findViewById(R.id.rl_login);
        rl_debugger = (RelativeLayout)getView().findViewById(R.id.rl_debugger);
        rl_offline_data_setting = (RelativeLayout) getView().findViewById(R.id.rl_offline_data_setting);

        rl_myinfo.setOnClickListener(this);
        rl_pwdedit.setOnClickListener(this);
        rl_authorization.setOnClickListener(this);
        rl_machine.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_server.setOnClickListener(this);
        rl_check.setOnClickListener(this);
        rl_cache.setOnClickListener(this);
        rl_login.setOnClickListener(this);
        rl_debugger.setOnClickListener(this);
        rl_offline_data_setting.setOnClickListener(this);
        
        iv_avatar = (ImageView) getView().findViewById(R.id.iv_avatar);
        tv_name = (TextView) getView().findViewById(R.id.tv_name);
        iv_sex = (ImageView) getView().findViewById(R.id.iv_sex);
        tv_company = (TextView) getView().findViewById(R.id.tv_company);
        tv_check_status = (TextView) getView().findViewById(R.id.tv_check_status);

        rl_about.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(rl_debugger.getVisibility() == View.VISIBLE) {
                    rl_debugger.setVisibility(View.GONE);
                } else {
                    rl_debugger.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        
        // 版本更新
        mManager = new UpdateManager(getActivity());
        mManager.checkVersion(new UpdateManager.OnUpdateListener() {
            @Override
            public void onUpdateStatus(UpdateManager.UpgradeVersionInfo info) {
                upgradeVersionInfo = info;
                if(info.isUpdate) {
                    tv_check_status.setVisibility(View.VISIBLE);
                } else {
                    tv_check_status.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("hidden", hidden);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            hidden = savedInstanceState.getBoolean("hidden");
        }
    }
    
    public void refresh() {
        rl_pwdedit.setVisibility(View.VISIBLE);
        iv_sex.setVisibility(View.VISIBLE);

        String vatar_temp = PrefUserInfo.getInstance().getUserInfo("avatar");
        String username_temp = PrefUserInfo.getInstance().getUserInfo("username");
        String company = PrefUserInfo.getInstance().getUserInfo("department");
        String sex_temp = PrefUserInfo.getInstance().getUserInfo("sex");

        showUserAvatar(iv_avatar, vatar_temp);
        tv_name.setText(username_temp);
        tv_company.setText(company);

        if (StringUtil.isEmpty(sex_temp) || "男".equals(sex_temp)) {
            iv_sex.setImageResource(R.drawable.ic_sex_male);
        } else {
            iv_sex.setImageResource(R.drawable.ic_sex_female);
        }
    }

    private void showUserAvatar(ImageView imageView, String avatar) {
        if (StringUtil.isEmpty(avatar)) {
            return;
        }
        Glide.with(getActivity())
                .load(avatar)
                .into(imageView);
    }

    private boolean hidden;
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;

        if (!hidden) {
            refresh();
        } 
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();//切换账号
        if (i == R.id.rl_authorization) {
            getActivity().startActivity(new Intent(getActivity(), SettingAuthorActivity.class));
        } else if (i == R.id.rl_machine) {
            getActivity().startActivity(new Intent(getActivity(), SettingParamActivity.class));
        } else if (i == R.id.rl_about) {
            getActivity().startActivity(new Intent(getActivity(), SettingAboutActivity.class));
        } else if (i == R.id.rl_server) {
            getActivity().startActivity(new Intent(getActivity(), SettingServerActivity.class));
        } else if (i == R.id.rl_pwdedit) {
            getActivity().startActivity(new Intent(getActivity(), PwdEditActivity.class));
        } else if (i == R.id.rl_myinfo) {
            getActivity().startActivity(new Intent(getActivity(), MyUserInfoActivity.class));
        } else if (i == R.id.rl_login) {
            logout();
        } else if (i == R.id.rl_check) {
            checkUpdate();
        } else if (i == R.id.rl_cache) {
            doClearCache();
        } else if (i == R.id.rl_debugger) {
            sendReportLog();
        } else if (i == R.id.rl_offline_data_setting) {
            getActivity().startActivity(new Intent(getActivity(), SettingOfflineDataActivity.class));
        }
    }

    private void sendReportLog() {
        rl_debugger.setClickable(false);
        String[] crFiles = getLogReportFiles();
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));
            List<File> files = new ArrayList<>();
            for (String fileName : sortedFiles) {
                File file = new File(StorageUtil.getAppCachePath(), fileName);
                files.add(file);
            }
            SendReportAsyncTask task = new SendReportAsyncTask(new SendReportAsyncTask.OnSendReportListener() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onProgress(Integer... progress) {

                }

                @Override
                public void onPostExecute() {
                    rl_debugger.setClickable(true);
                    Toast.makeText(getActivity(), "上传日志完成", Toast.LENGTH_SHORT).show();
                }
            });
            task.execute(TelManagerUtil.getInstance().getDeviceId(), files);
        }
    }

    private String[] getLogReportFiles() {
        File filesDir = new File(StorageUtil.getAppCachePath());
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".log") || name.endsWith(".cr");
            }
        };
        return filesDir.list(filter);
    }

    private void logout() {
        String content = "是否退出当前登录的账户？";
        
        CustomAlertDialog.show(getActivity(), content, new OnDoingListener() {

            @Override
            public void onOK() {
                PrefSetting.getInstance().setChangeUser(true);
                CommonBussiness.getInstance().updateCurrentAccessRecordStatus(true);
                CommonBussiness.getInstance().uploadAppAccessRecord();
                getActivity().finish();
            }

            @Override
            public void onNG() {
            }                
        });
    }
    
    private void checkUpdate() {
        if(upgradeVersionInfo != null && !StringUtil.isEmpty(upgradeVersionInfo.prompt)) {
            CustomAlertDialog.show(getContext(), upgradeVersionInfo.prompt, new CustomAlertDialog.OnDoingListener() {
                @Override
                public void onOK() {
                    mManager.checkUpdate();
                }

                @Override
                public void onNG() {

                }
            });
        } else {
            mManager.checkUpdate();
        }
    }

    
    private void doClearCache() {
        String content = "将清空所有本软件缓存的数据...";
        
        CustomAlertDialog.show(getActivity(), content, new OnDoingListener() {

            @Override
            public void onOK() {
                clearCache();
            }

            @Override
            public void onNG() {
            }                
        });
    }
    
    private void clearCache() {
        FileUtil.deleteFile(StorageUtil.getCachePath());
        
        FileUtil.deleteFile(StorageUtil.getAttachPath());
        
        FileUtil.deleteFile(StorageUtil.getTempPath());
        
        Toast.makeText(getActivity(), "缓存数据已被清空！",
                Toast.LENGTH_SHORT).show();
    }

}
