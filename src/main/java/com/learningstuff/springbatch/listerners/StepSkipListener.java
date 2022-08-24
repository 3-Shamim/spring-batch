package com.learningstuff.springbatch.listerners;

import com.learningstuff.springbatch.models.Sample;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Component
public class StepSkipListener implements SkipListener<Sample, Number> {

    @OnSkipInRead
    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("StepSkipListener - onSkipInRead");
    }

    @OnSkipInWrite
    @Override
    public void onSkipInWrite(Number item, Throwable t) {
        System.out.println("StepSkipListener - afterWrite");
    }

    @OnSkipInProcess
    @Override
    public void onSkipInProcess(Sample item, Throwable t) {
        System.out.println("StepSkipListener - onWriteError");
    }

}