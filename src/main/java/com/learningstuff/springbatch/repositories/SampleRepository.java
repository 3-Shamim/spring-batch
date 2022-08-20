package com.learningstuff.springbatch.repositories;

import com.learningstuff.springbatch.models.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
}
