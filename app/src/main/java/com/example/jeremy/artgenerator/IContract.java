package com.example.jeremy.artgenerator;

import com.example.jeremy.artgenerator.constants.AnimationType;

public interface IContract {

    interface IView {
        //render animations
        void animate(AnimationType animationType);
    }

    interface IViewModel {
        //involves all view models
        void onClose();
    }
}
