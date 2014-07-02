package com.mk.apps.superhard;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.bindings.admob.GADAdSizeManager;
import org.robovm.bindings.admob.GADBannerView;
import org.robovm.bindings.admob.GADBannerViewDelegateAdapter;
import org.robovm.bindings.admob.GADRequest;
import org.robovm.bindings.admob.GADRequestError;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.badlogic.gdx.utils.Logger;
import com.mk.apps.superm.MainStarter;
import com.mk.apps.superm.MainStarter.IActivityRequestHandler;

public class IOSLauncher extends IOSApplication.Delegate implements IActivityRequestHandler {
	 private static final boolean USE_TEST_DEVICES = true;
	    private GADBannerView adview;
	    private IOSApplication iosApplication;
	    private static final Logger log = new Logger(IOSLauncher.class.getName(), Application.LOG_DEBUG);
    @Override
    protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();

    	config.orientationLandscape = true;
		config.orientationPortrait = false;
		iosApplication= new IOSApplication(new MainStarter(this), config);


		return iosApplication;
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

	public void initializeAds() {
            log.debug("Initalizing ads...");


            adview = new GADBannerView(GADAdSizeManager.smartBannerPortrait());
            adview.setAdUnitID("ca-app-pub-5203821854175806/3996403978"); //put your secret key here
            adview.setRootViewController(iosApplication.getUIViewController());
            iosApplication.getUIViewController().getView().addSubview(adview);

            final GADRequest request = GADRequest.request();
            if (USE_TEST_DEVICES) {
                final NSArray<?> testDevices = new NSArray<NSObject>(
                        new NSString(GADRequest.GAD_SIMULATOR_ID));
                request.setTestDevices(testDevices);
                log.debug("Test devices: " + request.getTestDevices());
            }

            adview.setDelegate(new GADBannerViewDelegateAdapter() {
                @Override
                public void didReceiveAd(GADBannerView view) {
                    super.didReceiveAd(view);
                    log.debug("didReceiveAd");
                }

                @Override
                public void didFailToReceiveAd(GADBannerView view,
                        GADRequestError error) {
                    super.didFailToReceiveAd(view, error);
                    log.debug("didFailToReceiveAd:" + error);
                }
            });

            adview.loadRequest(request);

            log.debug("Initalizing ads complete.");
        
    }
	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		Gdx.app.debug("showAds ", "showAds");
		initializeAds();

        final CGSize screenSize = UIScreen.getMainScreen().getBounds().size();
        double screenWidth = screenSize.width();

        final CGSize adSize = adview.getBounds().size();
        double adWidth = adSize.width();
        double adHeight = adSize.height();

        log.debug(String.format("Hidding ad. size[%s, %s]", adWidth, adHeight));

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) (bannerWidth / adWidth * adHeight);

        if(show) {
            adview.setFrame(new CGRect((screenWidth / 2) - adWidth / 2, 0, bannerWidth, bannerHeight));
        } else {
            adview.setFrame(new CGRect(0, -bannerHeight, bannerWidth, bannerHeight));
        }
	}
}