package com.learningstuff.springbatch.services;

import com.learningstuff.springbatch.models.Sample;
import com.learningstuff.springbatch.repositories.SampleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@AllArgsConstructor
@Service
public class SampleService {

    private SampleRepository sampleRepository;

    public List<Sample> findAll() {
        return sampleRepository.findAll();
    }

}
