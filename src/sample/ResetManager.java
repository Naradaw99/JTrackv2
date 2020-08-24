package sample;

import javafx.scene.control.ToggleButton;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ResetManager {
    ArrayList<ToggleButton> dailyList;
    ArrayList<ToggleButton> weeklyList;
    ArrayList<ToggleButton> monthlyList;

    private FileWriter resetFile = new FileWriter("resetFile.txt", true);
    private FileWriter resetFileWeekly = new FileWriter("resetFileWeekly.txt", true);
    private FileWriter resetFileMonthly = new FileWriter("resetFileMonthly.txt", true);
    //private FileReader resetFileRead = new FileReader("resetFile.txt");
    FileReader resetFileRead;

    private BufferedWriter bw;

    trackerController tc = new trackerController();

    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

    public ResetManager(ArrayList<ToggleButton> dailyList, ArrayList<ToggleButton> weeklyList, ArrayList<ToggleButton> monthlyList) throws IOException {
        this.dailyList = dailyList;
        this.weeklyList = weeklyList;
        this.monthlyList = monthlyList;
    }

    public void writeDaily() throws IOException {
        bw = new BufferedWriter(resetFile);

        resetFile = new FileWriter("resetFile.txt");
        bw.write("" + (now.getDayOfMonth()));
        bw.flush();
    }

    public void writeWeekly(Calendar time) throws IOException {
        resetFile = new FileWriter("resetFileWeekly.txt");
        bw = new BufferedWriter(resetFile);

        bw.write(String.valueOf((time.getTime())));
        bw.flush();

    }

    public void writeMonthly(Calendar time) throws IOException {
        resetFile = new FileWriter("resetFileMonthly.txt");
        bw = new BufferedWriter(resetFile);

        bw.write(String.valueOf((time.getTime())));
        bw.flush();

    }

    public void checkResetDaily() throws IOException {

        resetFileRead = new FileReader("resetFile.txt");
        BufferedReader br = new BufferedReader(resetFileRead);

        Integer line;
        try {
            line = Integer.parseInt(br.readLine());
        } catch (NumberFormatException e) {
            tc.reset(dailyList);
            writeDaily();
            line = Integer.parseInt(br.readLine());
        }

        if (!line.equals(now.getDayOfMonth())) {
            tc.reset(dailyList);
            writeDaily();
        }
    }

    public void checkResetWeekly(Calendar weeklyTime) throws IOException, ParseException {
        resetFileRead = new FileReader("resetFileWeekly.txt");
        BufferedReader br = new BufferedReader(resetFileRead);


        String line = br.readLine();
        if (line == null) {
            tc.reset(weeklyList);
            writeWeekly(weeklyTime);
            line = br.readLine();
        }

        if (!line.equals(weeklyTime.getTime())){
            writeWeekly(weeklyTime);
        }

        Calendar readDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        readDate.setTime(sdf.parse(line));//

        TimeZone tz = TimeZone.getTimeZone("UTC");
        Calendar now = Calendar.getInstance(tz);


        if (now.after(readDate)) {
            tc.reset(weeklyList);

        }


    }

    public void checkResetMonthly(Calendar monthlyTime) throws IOException, ParseException {
        resetFileRead = new FileReader("resetFileMonthly.txt");
        BufferedReader br = new BufferedReader(resetFileRead);


        String line = br.readLine();
        if (line == null) {
            tc.reset(monthlyList);
            writeMonthly(monthlyTime);
            line = br.readLine();
        }

        if (!line.equals(monthlyTime.getTime())){
            writeMonthly(monthlyTime);
        }

        Calendar readDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        readDate.setTime(sdf.parse(line));//

        TimeZone tz = TimeZone.getTimeZone("UTC");
        Calendar now = Calendar.getInstance(tz);


        if (now.after(readDate)) {
            tc.reset(monthlyList);

        }


    }
}
