package com.sjjapps.partygame.controllers;

/**
 * Created by Shane Jansen on 11/26/15.
 */
public interface SjjController {
    void update(float deltaTime);
    void didFinishLoading();
    void resize(int width, int height);
    void dispose();
}
