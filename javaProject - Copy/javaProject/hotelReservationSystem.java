import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;


 class HotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "8651982656";

    public static void main(String[] args) {
        try {
            Class.forName("con.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);

            while (true) {
                System.out.println();
                System.out.println("Hotel Management System");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation ");
                System.out.println("3. Get Room Number ");
                System.out.println("4. Update Reservation ");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.println("choose an option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        reserveRoom(con, sc);
                        break;

                    case 2:
                        viewReservation(con,sc);
                        break;

                    case 3:
                        getRoomNumber(con, sc);
                        break;

                    case 4:
                        updateReservation(con, sc);
                        break;

                    case 5:
                        deleteReservation(con, sc);
                        break;
                    case 0:
                        exits();
                        sc.close();
                        return;
                    default:
                        System.out.println("invalid choice. try again!");


                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static void reserveRoom(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter guest name:");
            String guestName = sc.nextLine();
            sc.nextLine();
            System.out.println("Enter room number:");
            int roomNumber = sc.nextInt();
            System.out.println("Enter contact number:");
            String contactNumber = sc.next();


            String sql = "INSERT INTO reservation (guest_name,room_number, contact_number,)" +
                    "VALUES (" + guestName + ", " + roomNumber + ", " + contactNumber + " );";

            try (Statement statement = connection.createStatement()) {
                int affectedRow = statement.executeUpdate(sql);

                if (affectedRow > 0) {
                    System.out.println("Reservation successfully");
                } else {
                    System.out.println("Reservation failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void viewReservation(Connection con, Scanner sc) throws SQLException {
        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date;";

        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Current Reservation:");
            System.out.println("ReservationId | Guest | Room number | contact number | Reservation date");

            while (resultSet.next()) {
                int reservation_id = resultSet.getInt("reservation_id");
                String guest_name = resultSet.getString("guest_name");
                int room_number = resultSet.getInt("room_number");
                String contact_number = resultSet.getString("contact_number");
                String reservation_date = resultSet.getTimestamp("reservation_date").toString();

                //format to display the reservation data in table
                System.out.println(reservation_id+ "|" + guest_name + "|" + room_number + "|" + contact_number + "|" +  reservation_date);
            }
            System.out.println();
        }
    }

    public static void getRoomNumber(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter reservation id:");
            int reservationId = sc.nextInt();
            System.out.println("Enter guest name:");
            String guestName = sc.next();

            String sql = "SELECT room_number FROM reservation" + "WHERE reservation_id= " + reservationId + " AND guestName=" + guestName ;
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room number for reservation id:" + reservationId + "and guest" + guestName + "is:" + roomNumber);

                } else {
                    System.out.println("Reservation not found for the given ID and guest name");

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void updateReservation(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter registration id to update:");
            int reservationId = sc.nextInt();
            sc.nextLine();


            if (!reservationExists(connection, reservationId)) {
                System.out.println("reservation not found for given id");
                return;
            }
            System.out.println("Enter new guest name:");
            String newGuestName = sc.nextLine();
            System.out.println("Enter new room number:");
            int newRoomNumber = sc.nextInt();
            System.out.println("Enter new contact number:");
            String newContactNumber = sc.next();


            String sql = "UPDATE reservation SET guest_name=" + newGuestName + "," + "room_number= " + newRoomNumber + ", " +
                    "contact_number=" + newContactNumber + "," +
                    "WHERE reservation_id=" + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectRows = statement.executeUpdate(sql);

                if (affectRows > 0) {
                    System.out.println("Reservation updated successfully !");

                } else {
                    System.out.println("Reservation updated failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void deleteReservation(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter reservation id to delete");
            int reservationId = sc.nextInt();


            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given id");
                return;
            }
            String sql = "DELETE FROM reservation WHERE reservation_id=" + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectRows = statement.executeUpdate(sql);

                if (affectRows > 0) {
                    System.out.println("reservation deleted successfully");

                } else {
                    System.out.println("reservation deleted failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static boolean reservationExists(Connection connection, int reservationId) {
        try {
            String sql = "SELECT reservation_id FROM reservation WHERE reservation_id=" + reservationId;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                return resultSet.next();   //if there result the reservation exists

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    private static boolean reservationExists(Connection connection, int reservationId) {
//        try {
//            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id= " + reservationId;
//
//
//            try (Statement statement = connection.createStatement();
//                 ResultSet resultSet = statement.executeQuery(sql)) {
//                return resultSet.next();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }


    public static void exits() throws InterruptedException {
        System.out.println("Existing System");
        int i = 5;
        while (i != 0) {
            System.out.println(".");
            Thread.sleep(450);
            i--;

        }
        System.out.println();
        System.out.println("Thanks you for using hotel management system");
    }
}
