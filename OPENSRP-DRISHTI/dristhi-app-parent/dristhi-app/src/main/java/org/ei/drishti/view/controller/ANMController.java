package org.ei.drishti.view.controller;

import org.ei.drishti.service.ANMService;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.HomeContext;

import com.google.gson.Gson;

public class ANMController {
    private final ANMService anmService;
    private final Cache<String> cache;
    private final Cache<HomeContext> nativeCache;
    private static final String HOME_CONTEXT = "homeContext";
    private static final String NATIVE_HOME_CONTEXT = "nativeHomeContext";


    public ANMController(ANMService anmService, Cache<String> cache, Cache<HomeContext> homeContextCache) {
        this.anmService = anmService;
        this.cache = cache;
        this.nativeCache = homeContextCache;
    }

    public String get() {
        return cache.get(HOME_CONTEXT, new CacheableData<String>() {
            @Override
            public String fetch() {
                return new Gson().toJson(new HomeContext(anmService.fetchDetails()));
            }
        });
    }

    public HomeContext getHomeContext() {
        return nativeCache.get(NATIVE_HOME_CONTEXT, new CacheableData<HomeContext>() {
            @Override
            public HomeContext fetch() {
                return new HomeContext(anmService.fetchDetails());
            }
        });
    }
}
