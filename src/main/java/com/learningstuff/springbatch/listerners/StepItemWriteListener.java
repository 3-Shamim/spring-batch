package com.learningstuff.springbatch.listerners;

import com.learningstuff.springbatch.models.Sample;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Component
public class StepItemWriteListener implements ItemWriteListener<Sample> {

    @BeforeWrite
    @Override
    public void beforeWrite(List<? extends Sample> items) {
        System.out.println("ItemWriteListener - beforeWrite");
    }

    @AfterWrite
    @Override
    public void afterWrite(List<? extends Sample> items) {
        System.out.println("ItemWriteListener - afterWrite");
    }

    @OnWriteError
    @Override
    public void onWriteError(Exception exception, List<? extends Sample> items) {
        System.out.println("ItemWriteListener - onWriteError");
    }

}