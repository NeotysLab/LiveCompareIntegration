import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.Test;

import static com.neotys.tricentis.workloadParser.app.Constants.SPARK_APPNAME;
import static com.neotys.tricentis.workloadParser.app.Constants.SPARK_PATH;

public class converterTest {

    @Test
    public void defineRatio() {
        int total=33;

        int count=11;

        double ratio=count*1.0d/total;

        System.out.println(ratio);
    }

    @Test
    public void createSeesion()
    {
        SparkSession sparkSession = SparkSession.builder()
                .appName(SPARK_APPNAME)
                .master("local[*]")
                .config("spark.sql.warehouse.dir", "file:/home/hrexed/tmp7")
                .getOrCreate();

        System.out.println(sparkSession.toString());

        sparkSession.close();

    }
}
