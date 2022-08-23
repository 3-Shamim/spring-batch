package com.learningstuff.springbatch.jobs.sample_csv_to_db;

import com.learningstuff.springbatch.models.Sample;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim
 * Date: ২২/৮/২২
 * Time: ১:১০ PM
 * Email: mdshamim723@gmail.com
 */


public class SampleCSVImportProcess implements ItemProcessor<Sample, Sample> {

    @Override
    public Sample process(Sample sample) throws Exception {
        return sample;
    }

}
