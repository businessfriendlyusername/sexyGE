package scripts.API.BelvisGE;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceLookup {

    public static int getPrice(final int itemId) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://" +
                "services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=" + itemId).openStream()))) {
            Matcher matcher = Pattern.compile(".*\"price\":\"?(\\d+\\,?\\.?\\d*)([k|m]?)\"?},\"today\".*").matcher(reader.readLine());
            if (matcher.matches()) {
                double price = Double.parseDouble(matcher.group(1).replace(",", ""));
                String suffix = matcher.group(2);
                return (int) (suffix.isEmpty() ? price : price * (suffix.charAt(0) == 'k' ? 1000 : 1000000));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}