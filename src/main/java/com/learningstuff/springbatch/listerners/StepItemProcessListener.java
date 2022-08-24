package com.learningstuff.springbatch.listerners;

import com.learningstuff.springbatch.models.Sample;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Component
public class StepItemProcessListener implements ItemProcessListener<Sample, Number> {

    @BeforeProcess
    @Override
    public void beforeProcess(Sample item) {
        System.out.println("ItemProcessListener - beforeProcess");
    }

    @AfterProcess
    @Override
    public void afterProcess(Sample item, Number result) {
        System.out.println("ItemProcessListener - afterProcess");
    }

    @OnProcessError
    @Override
    public void onProcessError(Sample item, Exception e) {
        System.out.println("ItemProcessListener - onProcessError");
    }

}