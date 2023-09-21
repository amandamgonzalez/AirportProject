import java.io.*;
import java.util.*;
import java.util.Arrays;

class date {
    int day;
    int month;
    int year;

    // how to add the 0 infront
    // String with3digits = String.format("%02d", num);

    public date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
}

class managingdate {
    public date comparedates(date currdate, date flightdate) {
        date oldestdate = null;
        int currd = currdate.year * 10000 + currdate.month + currdate.day;
        int flightd = flightdate.year + 10000 + flightdate.month + flightdate.day;
        if (currd < flightd) {
            oldestdate = currdate;
        }
        if (currd > flightd) {
            oldestdate = flightdate;
        }
        return oldestdate;
    }

    public static void setdate(String userstr) {
        String[] temp = userstr.split(" ");
        String[] inputdate = temp[1].split("-");
        int day = Integer.parseInt(inputdate[0]);
        int month = Integer.parseInt(inputdate[1]);
        int year = Integer.parseInt(inputdate[2]);
        date todaysdate = new date(day, month, year);
    }
}

// creating each aiport info
class airport {
    String airportid;
    String country;
    String city;
    // int flights;

    public airport(String airportid, String country, String city) {
        this.airportid = airportid;
        this.country = country;
        this.city = city;
        // this.flights = flights;
    }
}

class managingairport {

    // helper method, getting a subarray of an array
    public static <T> T[] subArray(T[] array, int beg, int end) {
        return Arrays.copyOfRange(array, beg, end + 1);
    }

    public static airport fixairport(String userstr) {
        // fixing the city
        String[] inputarr = userstr.split("\\s+");
        int beg = 3, end = inputarr.length - 1;
        String[] cityarray = subArray(inputarr, beg, end);
        String delimiter = " ";
        String newcity = String.join(delimiter, cityarray);

        airport currairport = new airport(inputarr[1], inputarr[2], newcity);
        return currairport;
    }

    public static void addairport(String userstr, Vector<airport> airportvect) {
        // current airport fixed
        airport currairport = fixairport(userstr);

        // if too many airports
        if (airportvect.size() == 2) {
            System.out.print("too many airports");
            return;
        }

        // if not all caps
        if ((currairport.airportid).equals(currairport.airportid.toUpperCase()) == false) {
            System.out.print("Invalid Airport ID");
            return;
        }

        if (airportvect.size() == 0 && (currairport.airportid).equals(currairport.airportid.toUpperCase()) == true) {
            airportvect.addElement(currairport);
        }

        else {
            boolean duplicatedair = false;

            Iterator<airport> airportitr = airportvect.iterator();
            while (airportitr.hasNext()) {
                if (((airportitr.next()).airportid).equals(currairport.airportid)) {
                    System.out.println("Duplicate airport");
                    duplicatedair = true;
                    break;
                }
            }
            if (duplicatedair == false) {
                airportvect.addElement(currairport);
            }
        }
    }

    public static void insertsort(Vector<airport> airportvect) {
        for (int n = 1; n < airportvect.size(); n++) {
            for (int x = n - 1; x >= 0; x--) {
                int compare = ((airportvect.get(x)).airportid).compareTo((airportvect.get(n)).airportid);

                // if x is greater than x essentially
                // int compare = a.compareTo(b);

                if (compare > 0) {
                    airport temp = airportvect.get(x);
                    airportvect.set(x, airportvect.get(n));
                    airportvect.set(n, temp);
                    n--;
                } else {
                    break;
                }
            }
        }
    }

    public static void listairports(Vector<airport> airportvect, String userstr) {
        if (userstr.length() == 1) {

            // System.out.println("Vector elements: ");
            // for (int i = 0; i < airportvect.size(); i++) {
            // System.out.print((airportvect.get(i)).airportid + " ");
            // }
            insertsort(airportvect);
            System.out.println("\n" + "Vector elements after sorting: ");
            for (int i = 0; i < airportvect.size(); i++) {
                System.out.print((airportvect.get(i)).airportid + " " + (airportvect.get(i)).city + " "
                        + (airportvect.get(i)).country + "  \n");
            }
        } else {

            String[] inputarr = userstr.split("\\s+");
            String[] usercopy = Arrays.copyOfRange(inputarr, 1, inputarr.length);
            // checker
            // System.out.println(Arrays.toString(usercopy));

            // new vector
            Vector<airport> foundairport = new Vector<airport>(2);

            for (String id : usercopy) {
                boolean found = false;
                Iterator<airport> airportitr = airportvect.iterator();
                while (airportitr.hasNext()) {
                    airport currairport = airportitr.next();
                    if ((currairport.airportid).equals(id)) {
                        found = true;
                        foundairport.addElement(currairport);
                        break;
                    }
                }
                if (found == false) {
                    System.out.println(id + ": no such airport id");
                }
            }

            // go over new vector to print all the info after seeing all the airports
            // in fact do exist
            // if found is TRUE and only then change thiss
            Iterator<airport> foundIterator = foundairport.iterator();
            while (foundIterator.hasNext()) {
                airport foundair = foundIterator.next();
                System.out.println(foundair.airportid + " " + foundair.city + " " +
                        foundair.country + " ");
            }
        }
    }
}

class flight {
    String flightCode;
    String departureAirportID;
    String arrivalAirportID;
    String departureDate;
    String departureTime;
    int duration;
    int capacity;

    public flight(String flightCode, String departureAirportID, String arrivalAirportID, String departureDate,
            String departureTime, int duration, int capacity) {
        this.flightCode = flightCode;
        this.departureAirportID = departureAirportID;
        this.arrivalAirportID = arrivalAirportID;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.duration = duration;
        this.capacity = capacity;
    }
}

class managingflights {
    public static boolean fixflight(String userstr) {
        String[] userinput = userstr.split("\\s+");
        String flightcode = userinput[1];

        String beg = (flightcode).substring(0, 2);
        if (beg.equals(beg.toUpperCase()) == false) {
            System.out.print("invalid flight code");
            return false;
        }

        String end = (flightcode).substring(3, (flightcode.length()));
        for (int i = 0; i < end.length(); i++) {
            if (Character.isDigit(end.charAt(i)) == false) {
                System.out.println("invalid flight code");
                // return position of digit
                return false;
            }
        }
        return true;
    }

    public static void addflight(String userstr) {
        boolean temp = fixflight(userstr);
        if (temp == true) {
            String[] userinput = userstr.split("\\s+");
            int dur = Integer.parseInt(userinput[6]);
            int cap = Integer.parseInt(userinput[7]);
            flight currflight = new flight(userinput[1], userinput[2], userinput[3], userinput[4], userinput[5],
                    dur, cap);
            System.out.println(currflight.flightCode);

        }
    }
}
// finish here

class airportrunning {
    public static void main(String[] args) {
        // creating the vector that stores all the airports
        Vector<airport> airportvect = new Vector<airport>(2);
        date todaysdate = new date(1, 1, 2022);
        Scanner myObj = new java.util.Scanner(System.in);

        while (true) {
            System.out.println("\n" + "Enter a command: ");
            String userstr = myObj.nextLine();
            userstr = userstr.trim();

            // TERMINATING (Q)
            if (userstr.equals("q")) {
                myObj.close();
                System.out.println("Now Terminating Program");
                break;
            }

            // ADDDING AIRPORTS (A)
            else if ((userstr.substring(0, 1)).equals("a")) {
                managingairport.addairport(userstr, airportvect);
            }

            // LISTS AIRPORS (L)
            else if ((userstr.substring(0, 1)).equals("l")) {
                managingairport.listairports(airportvect, userstr);

            }

            // ADDING FLIGHTS OR LISTINGS FLIGHTS
            else if ((userstr.substring(0, 1)).equals("v")) {
                managingflights.addflight(userstr);
            }

            // SET THE DATE
            else if ((userstr.substring(0, 1)).equals("t")) {
                managingdate.setdate(userstr);
                System.out.println(todaysdate.day + todaysdate.month + todaysdate.year);
            }

            // IN CASE THE COMMAND DOESNT EXIST
            else {
                System.out.print("Command doesn't yet exist ");
            }
        }
    }
}
