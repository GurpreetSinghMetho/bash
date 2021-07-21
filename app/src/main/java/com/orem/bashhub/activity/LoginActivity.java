package com.orem.bashhub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.orem.bashhub.R;
import com.orem.bashhub.data.SignUpPOJO;
import com.orem.bashhub.databinding.ActivityLoginBinding;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@SuppressWarnings("ConstantConditions")
public class LoginActivity extends BaseActivity /*implements LoginStateController.OnLoginStateChangedListener*/ {

    ActivityLoginBinding binding;
    private static final String EXTERNAL_ID_QUERY = "{me{externalId,displayName,bitmoji{avatar}}}";
    private LoginButton fbLoginBT;
    private CallbackManager callbackManager;
    String token, socialemail, socailId, socialFname, sociaLname, socialImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
    }

    private void init() {
        Utils.loadImage(mContext, R.drawable.ic_star_bg, binding.ivBg, 0);
        fbLoginBT = findViewById(R.id.facebookBT);
        binding.loginBT.setOnClickListener(this);
        binding.signUP.setOnClickListener(this);
        binding.snapchatIV.setOnClickListener(this);
        binding.tvForgot.setOnClickListener(this);
        binding.ivFacebook.setOnClickListener(this);
        binding.tvTrouble.setOnClickListener(this);
        binding.ivNumber.setOnClickListener(this);
        binding.ivNumberSignUp.setOnClickListener(this);
        Utils.underlineTextView(binding.tvTrouble);
        Utils.underlineTextView(binding.ivNumberSignUp);
        facebookLogin();
        LoginManager.getInstance().logOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //SnapLogin.getLoginStateController(getApplicationContext()).addOnLoginStateChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBT:
                if (checkValidation()) apiLogin();
                break;
            case R.id.signUP:
                Intent intent = new Intent(this, GetStartedActivity.class);
                startActivity(intent);
                break;
            case R.id.snapchatIV:
                //SnapLogin.getAuthTokenManager(this).startTokenGrant();
                break;
            case R.id.tvForgot:
                mTinyDB.putString(Const.LOGIN_TYPE, Const.ZERO);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.SIGNUP_DATA, new SignUpPOJO());
                startNewActivity(PhoneActivity.class, false, false, bundle);
                break;
            case R.id.ivFacebook:
                LoginManager.getInstance().logOut();
                fbLoginBT.performClick();
                break;
            case R.id.tvTrouble:
                mTinyDB.putString(Const.LOGIN_TYPE, Const.ZERO);
                startNewActivity(TroubleLoginActivity.class, false, false, null);
                break;
            case R.id.ivNumber:
                mTinyDB.putString(Const.LOGIN_TYPE, Const.ZERO);
                SignUpPOJO data = new SignUpPOJO();
                data.setSignUp(false);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Const.SIGNUP_DATA, data);
                startNewActivity(PhoneActivity.class, false, false, bundle1);
                break;
            case R.id.ivNumberSignUp:
                mTinyDB.putString(Const.LOGIN_TYPE, Const.ONE);
                SignUpPOJO data1 = new SignUpPOJO();
                data1.setGoBack(false);
                data1.setSignUp(true);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable(Const.SIGNUP_DATA, data1);
                startNewActivity(UsernameActivity.class, false, false, bundle2);
                break;
        }
    }

    /*@Override
    public void onLoginSucceeded() {
        loadExternalId();
    }

    private void loadExternalId() {
        SnapLogin.fetchUserData(this, EXTERNAL_ID_QUERY, null, new FetchUserDataCallback() {
            @Override
            public void onSuccess(@Nullable UserDataResponse userDataResponse) {
                if (userDataResponse == null || userDataResponse.hasError()) {
                    return;
                }
                apiSocialLogin(userDataResponse.getData().getMe().getExternalId(),
                        userDataResponse.getData().getMe().getDisplayName(), "", "", Const.SOCIAL_SNAPCHAT);
            }

            @Override
            public void onFailure(boolean isNetworkError, int statusCode) {
                // handle error
            }
        });
    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onLogout() {

    }*/

    /*This Method is used to getting detail from facebook loggedIn user app*/
    private void facebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        fbLoginBT.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
                            if (object != null) {
                                try {
                                    socialemail = object.optString("email") != null ? "" : object.optString("email");
                                    socailId = object.optString("id");
                                    socialFname = object.optString("first_name") == null ? "" : object.optString("first_name");
                                    sociaLname = object.optString("last_name") == null ? "" : object.optString("last_name");
                                    socialImage = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                apiSocialLogin(socailId, socialFname, sociaLname, socialemail, Const.SOCIAL_FACEBOOOK);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,first_name,last_name,picture.type(normal)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                try {
                    log("FB LoginActivity" + exception.getCause().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*public void getFbImage() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
            if (object != null) {
                try {
                    String url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    Utils.showLog("Url : " + url);
                    Utils.loadImage(mContext, url, binding.ivBg, 0);
                } catch (JSONException e) {
                    Utils.showLog("Exp : "+e.getMessage());
                }
            }else {
                Utils.showLog("Not found");
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,first_name,last_name,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean checkValidation() {
        if (binding.etEmail.getText().toString().isEmpty()) {
            Utils.showMessageDialog(mContext, "", getString(R.string.enter_no_email_name));
            return false;
        } else if (binding.etPassword.getText().toString().isEmpty()) {
            Utils.showMessageDialog(mContext, "", getString(R.string.enter_password));
            return false;
        } else {
            return true;
        }
    }

    private void apiLogin() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiLogin(mContext,
                binding.etEmail.getText().toString(), binding.etPassword.getText().toString()), true, false));
    }

    private void apiSocialLogin(String social_id, String fName, String lName, String email, String social_type) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestBasic(mContext, Const.apiSocialLogin(mContext,
                social_id, "", email, fName, lName, "", "", social_type), true, false));
    }

    private void apiGetUser(String token) {
        registerEventBus();
        this.token = token;
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiGetUser(mContext, token), true, false));
    }

    @Subscribe
    public void apiLoginRes(Events.GetBasicData res) {
        unRegisterEventBus();
        if (res.getData().data.token != null) {
            apiGetUser("Bearer " + res.getData().data.token);
        } else {
            mTinyDB.putString(Const.LOGIN_TYPE, Const.ONE);
            SignUpPOJO data = new SignUpPOJO();
            data.setSocialID(socailId);
            data.setFname(socialFname);
            data.setLname(sociaLname);
            data.setEmail(socialemail);
            data.setImage(socialImage);
            data.setGoBack(false);
            data.setSignUp(true);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.SIGNUP_DATA, data);
            startNewActivity(PhoneActivity.class, false, false, bundle);
        }
    }

    @Subscribe
    public void apiGetUserRes(Events.GetUserData res) {
        unRegisterEventBus();
        Const.setToken(mContext, token);
        Const.setLoggedInUser(mContext, res.getData().data);
        startNewActivity(MainActivity.class, true, true, null);
    }
}
