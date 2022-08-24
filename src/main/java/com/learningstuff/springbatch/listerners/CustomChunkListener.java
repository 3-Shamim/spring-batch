package com.learningstuff.springbatch.listerners;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@Component
public class CustomChunkListener implements ChunkListener {

    @AfterChunk
    @Override
    public void afterChunk(ChunkContext context) {
        System.out.println("Called afterChunk().");
    }

    @BeforeChunk
    @Override
    public void beforeChunk(ChunkContext context) {
        System.out.println("Called beforeChunk().");
    }

    @AfterChunkError
    @Override
    public void afterChunkError(ChunkContext context) {
        System.out.println("Called afterChunkError().");
    }

}