package org.ei.drishti.view.activity;

import static org.ei.drishti.event.Event.ON_PHOTO_CAPTURED;

import org.ei.drishti.event.CapturedPhotoInformation;
import org.ei.drishti.event.Listener;
import org.ei.drishti.view.controller.EligibleCoupleDetailController;

public class EligibleCoupleDetailActivity extends SecuredWebActivity {
    private Listener<CapturedPhotoInformation> photoCaptureListener;

    @Override
    protected void onInitialization() {
        String caseId = (String) getIntent().getExtras().get("caseId");

        webView.addJavascriptInterface(new EligibleCoupleDetailController(this, caseId, context.allEligibleCouples(),
                context.allTimelineEvents()), "context");
        webView.loadUrl("file:///android_asset/www/ec_detail.html");

        photoCaptureListener = new Listener<CapturedPhotoInformation>() {
            @Override
            public void onEvent(CapturedPhotoInformation data) {
                if (webView != null) {
                    webView.loadUrl("javascript:pageView.reloadPhoto('" + data.entityId() + "', '" + data.photoPath() + "')");
                }
            }
        };
        ON_PHOTO_CAPTURED.addListener(photoCaptureListener);
    }
}
