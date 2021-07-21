package com.orem.bashhub.fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orem.bashhub.R;
import com.orem.bashhub.adapter.CostumesAdapter;
import com.orem.bashhub.data.FacePOJO;
import com.orem.bashhub.databinding.FragmentChangeOutfitBinding;
import com.orem.bashhub.interfaces.OnCostumePick;
import com.orem.bashhub.utils.Const;
import com.orem.bashhub.utils.MyImagePicker;
import com.orem.bashhub.utils.PaletteColor;
import com.orem.bashhub.utils.Utils;
import com.orem.bashhub.utils.apiinterface.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ChangeOutfitFragment extends BaseFragment {

    private FragmentChangeOutfitBinding binding;
    private String costume_id = Const.ONE, selectedFile = "", selectedFace = "";
    private Bitmap selectedBitmap = null;
    private FacePOJO.Data face;

    private OnCostumePick listener = new OnCostumePick() {
        @Override
        public void OnCostumePick(int position, int drawable) {
            Utils.loadImage(mContext, drawable, binding.ivBody, 0);
            costume_id = "" + (position + 1);
        }
    };

    public ChangeOutfitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_outfit, container, false);
        ini();
        return binding.getRoot();
    }

    @SuppressLint("CheckResult")
    private void ini() {
        Utils.loadImage(mContext, R.drawable.ic_costumes_bg, binding.ivBG, 0);
        Utils.loadImage(mContext, Const.getCostumeBody(Const.getLoggedInUser(mContext).costume_id), binding.ivBody, 0);
        Utils.loadImage(mContext, Const.IMAGE_BASE + Const.getLoggedInUser(mContext).avtar_img, binding.ivFace, R.drawable.ic_purple_default_face);
        binding.ivBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        binding.btDone.setOnClickListener(v -> getBitmap());
        binding.rvCostumes.setAdapter(new CostumesAdapter(mContext, listener));
        binding.ivFace.setOnClickListener(v -> showImagePicker());
        new Handler().postDelayed(() -> binding.rvCostumes.smoothScrollToPosition(Const.getLoggedInUser(mContext).costume_id - 1), 100);
    }

    private void showImagePicker() {
        registerEventBus();
        MyImagePicker.selectImage(mContext, false, getString(R.string.face_detection_msg));
    }

    private void getBitmap() {
        binding.rlBitmoji.setDrawingCacheEnabled(true);
        binding.rlBitmoji.buildDrawingCache();
        Bitmap bm = binding.rlBitmoji.getDrawingCache();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bm,
                (int) (mContext.getResources().getDimension(R.dimen._90sdp)), (int) mContext.getResources().getDimension(R.dimen._199sdp), false);
        apiUpdateOutfit(Utils.getTempFilePath(mContext, scaledBitmap));
    }

    private void extractFacePartsOnBitmap() {
        try {
            Bitmap eyeBit = Bitmap.createBitmap(selectedBitmap,
                    (int) (face.faceLandmarks.pupilLeft.x), (int) face.faceLandmarks.pupilLeft.y,
                    (int) (3 * face.faceRectangle.width / 100), (int) (3 * face.faceRectangle.height / 100));
            double bigX = face.faceLandmarks.eyeRightBottom.x > face.faceLandmarks.eyeLeftBottom.x ? face.faceLandmarks.eyeRightBottom.x : face.faceLandmarks.eyeLeftBottom.x;
            double smallX = face.faceLandmarks.eyeRightBottom.x > face.faceLandmarks.eyeLeftBottom.x ? face.faceLandmarks.eyeLeftBottom.x : face.faceLandmarks.eyeRightBottom.x;
            Bitmap faceBit = Bitmap.createBitmap(selectedBitmap,
                    (int) (face.faceLandmarks.eyeLeftBottom.x), (int) face.faceLandmarks.eyeLeftBottom.y + 3,
                    (int) (bigX - smallX),
                    (int) (face.faceLandmarks.noseTip.y - face.faceLandmarks.eyeLeftBottom.y));
            String eyeColor = PaletteColor.findClosestPaletteColor(Utils.getDominantColor(eyeBit), Const.COLOR_EYE);
            String eyeBrowColor = PaletteColor.getEyeBrowColor(face.faceAttributes.hair.getHairColorName());
            String faceColor = PaletteColor.findClosestPaletteColor(Utils.getDominantColor(faceBit), Const.COLOR_FACE);
            String gender = face.faceAttributes.gender.toLowerCase().equals(Const.GENDER_MALE) ? Const.ONE : Const.TWO;
            String beard = face.faceAttributes.facialHair.beard > Const.BEARD_VALUE ? Const.ONE : Const.ZERO;
            apiFaceUrl(faceColor, eyeColor, eyeBrowColor, beard, gender);
        } catch (Exception e) {
            Utils.showLog("Exp : " + e.getMessage());
            Utils.showDialog(mContext, getString(R.string.face_exception), "", getString(R.string.try_again),
                    (dialogInterface, i) -> showImagePicker(), null, getString(R.string.not_now), "", null, false);
        }
    }

    private void apiUpdateOutfit(String file) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestUser(mContext, Const.apiUpdateOutfit(mContext, "", selectedFace, costume_id, file), true, false));
    }

    private void apiFaceAttr() {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestFaceAttr(mContext, Const.apiFaceAttr(mContext, Utils.getCompressFile(mContext, selectedBitmap)), true, false));
    }

    private void apiFaceUrl(String faceColor, String eyeColor, String eyeBrowColor, String beard, String gender) {
        registerEventBus();
        EventBus.getDefault().post(new Events.RequestFaceUrl(mContext, Const.apiFaceUrl(mContext,
                faceColor, eyeColor, eyeBrowColor, beard, gender), true, false));
    }

    @Subscribe
    public void apiFaceAttrRes(Events.GetFaceAttrData res) {
        unRegisterEventBus();
        if (res.getData().data.size() <= 0) {
            Utils.showDialog(mContext, getString(R.string.no_face_found), "", getString(R.string.try_again),
                    (dialogInterface, i) -> showImagePicker(), null, getString(R.string.not_now), "", null, false);
        } else if (res.getData().data.size() > 1) {
            Utils.showDialog(mContext, getString(R.string.multiple_face_found), "", getString(R.string.try_again),
                    (dialogInterface, i) -> showImagePicker(), null, getString(R.string.not_now), "", null, false);
        } else if (!res.getData().data.get(0).faceAttributes.glasses.equals(Const.NO_GLASSES)) {
            Utils.showDialog(mContext, getString(R.string.face_with_glasses), "", getString(R.string.try_again),
                    (dialogInterface, i) -> showImagePicker(), null, getString(R.string.not_now), "", null, false);
        } else {
            face = res.getData().data.get(0);
            extractFacePartsOnBitmap();
        }
    }

    @Subscribe
    public void apiFaceUrlRes(Events.GetFaceUrlData res) {
        unRegisterEventBus();
        selectedFace = res.getData().data.image;
        Utils.loadImage(mContext, Const.IMAGE_BASE + selectedFace, binding.ivFace, R.drawable.ic_purple_default_face);
    }

    @Subscribe
    public void apiUpdateProfile(Events.GetUserData res) {
        unRegisterEventBus();
        Const.setLoggedInUser(mContext, res.getData().data);
        mLiveModel.getUserLiveData().setValue(Const.getLoggedInUser(mContext));
        Utils.showMessageDialog(mContext, "", getString(R.string.outfit_update_success), (dialog, which) -> binding.ivBack.performClick());
    }

    @Subscribe
    public void getImagePath(Events.ImagePickerResult res) {
        unRegisterEventBus();
        selectedFile = res.getPath();
        try {
            selectedBitmap = Utils.getImageInPortrait(selectedFile);
            apiFaceAttr();
        } catch (Exception e) {
            Utils.showToast(mContext, getString(R.string.failed_to_fetch));
        }
    }
}
