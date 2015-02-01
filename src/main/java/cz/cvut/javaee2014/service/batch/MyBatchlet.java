/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.service.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import javax.batch.api.AbstractBatchlet;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.BatchStatus;
import javax.inject.Named;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Toms
 */
@Named
public class MyBatchlet extends AbstractBatchlet {
    @Override
    public String process() {
        Logger.getRootLogger().info(" ---------- Batch: Hello batchlet!");
        return BatchStatus.COMPLETED.toString();
    }
}