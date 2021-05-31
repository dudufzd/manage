package generate;

import com.generate.util.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-30");
        System.out.println(date);
        Date date1 = DateUtils.getToday();
        System.out.println(date1);

        System.out.println(">>>" + LocalDate.parse("2021-05-30", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        LocalDate date2 = LocalDate.parse("2021-05-30", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(date2);

        show(2);
    }

    private static void show(int a){
        a--;
        System.out.println(0);
        if(a == 1){
            return;
        }
        show(a);

    }
}
