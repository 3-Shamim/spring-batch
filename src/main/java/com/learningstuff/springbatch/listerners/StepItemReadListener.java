package com.learningstuff.springbatch.listerners;

import com.learningstuff.springbatch.models.Sample;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Component
public class StepItemReadListener implements ItemReadListener<Sample> {

    @BeforeRead
    @Override
    public void beforeRead() {
        System.out.println("ItemReadListener - beforeRead");
    }

    // @AfterRead  // I don't know why it create another [After Read]
    @Override
    public void afterRead(Sample item) {
        System.out.println("ItemReadListener - afterRead");
    }

    @OnReadError
    @Override
    public void onReadError(Exception ex) {
        System.out.println("ItemReadListener - onReadError");
    }

}