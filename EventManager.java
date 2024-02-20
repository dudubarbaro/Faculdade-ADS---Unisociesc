import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

class User {
  private String name;
  private String city;
  private String email;
  private List<Event> events;

  public User(String name, String city, String email) {
    this.name = name;
    this.city = city;
    this.email = email;
    this.events = new ArrayList<>();
  }

  public void addEvent(Event event) {
    events.add(event);
  }

  public List<Event> getEvents() {
    return events;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getCity() {
    return city;
  }
}

class Event {
  private String name;
  private String address;
  private String category;
  private LocalDateTime dateTime;
  private String description;
  private List<User> attendees;

  public Event(String name, String address, String category, LocalDateTime dateTime, String description) {
    this.name = name;
    this.address = address;
    this.category = category;
    this.dateTime = dateTime;
    this.description = description;
    this.attendees = new ArrayList<>();
  }

  public void addAttendee(User user) {
    attendees.add(user);
  }

  public List<User> getAttendees() {
    return attendees;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getCategory() {
    return category;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public String getDescription() {
    return description;
  }
}

public class EventManager {
  private List<User> users;
  private List<Event> events;

  public EventManager() {
    this.users = new ArrayList<>();
    this.events = new ArrayList<>();
  }

  public void addUser(User user) {
    users.add(user);
  }

  public void addEvent(Event event) {
    events.add(event);
  }

  public void saveEventsToFile(String filename) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
      for (Event event : events) {
        writer.println(event.getName() + ";" + event.getAddress() + ";" + event.getCategory() + ";" +
            event.getDateTime() + ";" + event.getDescription());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadEventsFromFile(String filename) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(";");
        Event event = new Event(parts[0], parts[1], parts[2], LocalDateTime.parse(parts[3]), parts[4]);
        events.add(event);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<Event> getEvents() {
    return events;
  }

  public List<Event> getEventsByCity(String city) {
    List<Event> eventsByCity = new ArrayList<>();
    for (Event event : events) {
      if (event.getAddress().contains(city)) {
        eventsByCity.add(event);
      }
    }
    return eventsByCity;
  }

  public List<Event> getEventsByCategory(String category) {
    List<Event> eventsByCategory = new ArrayList<>();
    for (Event event : events) {
      if (event.getCategory().equalsIgnoreCase(category)) {
        eventsByCategory.add(event);
      }
    }
    return eventsByCategory;
  }

  public List<Event> getEventsByDateTime(LocalDateTime dateTime) {
    List<Event> eventsByDateTime = new ArrayList<>();
    for (Event event : events) {
      if (event.getDateTime().isAfter(dateTime)) {
        eventsByDateTime.add(event);
      }
    }
    return eventsByDateTime;
  }

  public List<Event> getEventsByUser(User user) {
    List<Event> eventsByUser = new ArrayList<>();
    for (Event event : events) {
      if (event.getAttendees().contains(user)) {
        eventsByUser.add(event);
      }
    }
    return eventsByUser;
  }

  public static void main(String[] args) {
    EventManager eventManager = new EventManager();

    // Load events from file
    eventManager.loadEventsFromFile("events.data");

    // Add a user
    User user = new User("John Doe", "New York", "john.doe@example.com");
    eventManager.addUser(user);

    // Create and add events
    LocalDateTime dateTime1 = LocalDateTime.of(2024, 2, 20, 10, 0);
    Event event1 = new Event("Event 1", "123 Main St, New York", "Party", dateTime1, "Description of Event 1");
    event1.addAttendee(user);
    eventManager.addEvent(event1);

    LocalDateTime dateTime2 = LocalDateTime.of(2024, 2, 21, 12, 0);
    Event event2 = new Event("Event 2", "456 Elm St, New York", "Sports", dateTime2, "Description of Event 2");
    eventManager.addEvent(event2);

    // Save events to file
    eventManager.saveEventsToFile("events.data");

    // Get events by city
    List<Event> eventsInNewYork = eventManager.getEventsByCity("New York");
    System.out.println("Events in New York:");
    for (Event event : eventsInNewYork) {
      System.out.println(event.getName());
    }

    // Get events by category
    List<Event> sportsEvents = eventManager.getEventsByCategory("Sports");
    System.out.println("Sports Events:");
    for (Event event : sportsEvents) {
      System.out.println(event.getName());
    }

    // Get events by date/time
    LocalDateTime dateTime = LocalDateTime.of(2024, 2, 20, 11, 0);
    List<Event> eventsAfterDateTime = eventManager.getEventsByDateTime(dateTime);
    System.out.println("Events after " + dateTime + ":");
    for (Event event : eventsAfterDateTime) {
      System.out.println(event.getName());
    }

    // Get events by user
    List<Event> eventsForUser = eventManager.getEventsByUser(user);
    System.out.println("Events for user " + user.getName() + ":");
    for (Event event : eventsForUser) {
      System.out.println(event.getName());
    }
  }
}
