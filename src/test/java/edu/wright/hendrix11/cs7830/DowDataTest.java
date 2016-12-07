package edu.wright.hendrix11.cs7830;

import edu.wright.hendrix11.cs7830.machine.MultiStockMachine;
import org.apache.commons.math3.stat.regression.MultipleLinearRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;


/**
 * Created by Joe on 12/3/2016.
 */
public class DowDataTest {

    private static double[] x = {-10.00,-8.00,-6.00,-4.00,-2.00,0.00,2.00,4.00,6.00,8.00,10.00};
    private static double[] y = {-11.20,-8.96,-6.72,-4.48,-2.24,0.00,2.24,4.48,6.72,8.96,11.20};
    private static DowData data;

    @BeforeClass
    public static void beforeClass() throws IOException, ParseException {
        data = new DowData("dow_jones_index.csv");
    }

    @Test
    public void testGets() {
        System.out.println("First day of year:\t" + data.getFirstDayOfYear());
        System.out.println("Last day of year:\t" + data.getLastDayOfYear());
        System.out.println("Index return:\t" + data.getIndex());
        System.out.println("Best stock:\t" + data.getBestStock().getSymbol() + " at " + data.getBestStock().getPercentIncrease());
        System.out.println("Worst stock:\t" + data.getWorstStock().getSymbol() + " at " + data.getWorstStock().getPercentIncrease());
    }

    @Test
    public void testInvest() {
        for(Stock stock : data.getStocks()) {
            System.out.println("Invest $1000.00 in " + stock.getSymbol() + " get $" + stock.invest(1000_00) / 100.0);
        }
    }

    @Test
    public void test() {
        SimpleRegression simple = new SimpleRegression(true);

        for(int i = 0; i < x.length; i++) {
            simple.addData(x[i], y[i]);
        }

        for(int i = 0; i < x.length; i++) {
            System.out.println("x\t" + x[i] + "\ty\t" + y[i] + "\tg\t" + simple.predict(x[i]));
        }
    }

    @Test
    public void test2() {
        SimpleRegression simple = new SimpleRegression(true);

        double[][] xy = {y,x};

        simple.addData(xy);

        for(int i = 0; i < x.length; i++) {
            System.out.println("x\t" + x[i] + "\ty\t" + y[i] + "\tg\t" + simple.predict(x[i]));
        }
    }

    @Test
    public void test3() {
        OLSMultipleLinearRegression multi = new OLSMultipleLinearRegression();

        double[] y = new double[]{11.0, 12.0, 13.0, 14.0, 15.0, 16.0};
        double[][] x = new double[6][];
        x[0] = new double[]{0,   0, 0, 0, 0};
        x[1] = new double[]{2.0, 0, 0, 0, 0};
        x[2] = new double[]{0, 3.0, 0, 0, 0};
        x[3] = new double[]{0, 0, 4.0, 0, 0};
        x[4] = new double[]{0, 0, 0, 5.0, 0};
        x[5] = new double[]{0, 0, 0, 0, 6.0};
        multi.newSampleData(y, x);

        double[] params = multi.estimateRegressionParameters();

        System.out.println(params.length);

        for(double[] ex : x) {
            double sum = params[0];

            for(int i = 0; i < ex.length; i++) {
                sum += ex[i] * params[i + 1];
            }

            System.out.println(sum);
        }
    }
}