package com.university.discretas.controller;

import com.university.discretas.entity.Event;
import com.university.discretas.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log
@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Event> events = eventService.findAll();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{date}")
    public ResponseEntity<?> findAllByDate(@PathVariable String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateFormat = LocalDate.parse(date, formatter);
        List<Event> events = eventService.findAllByDate(dateFormat);
        return ResponseEntity.ok(events);
    }

    @GetMapping(value = "/page/{page}/size/{size}", headers = {"Authorization"})
    public ResponseEntity<?> findAll(@PathVariable Integer page,
                                     @PathVariable Integer size,
                                     @RequestParam(required = false) String name) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> events;

        if (Objects.isNull(name)) {
            events = eventService.findAll(pageable);
        } else {
            events = eventService.findAllByLocationContainingIgnoreCase(name, pageable);
        }

        return ResponseEntity.ok(events);
    }

    @GetMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Event event = eventService.findById(id).orElse(null);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping(value = "/{id}", headers = {"Authorization"})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Event current = eventService.findById(id).orElse(null);

        if (Objects.isNull(current)) {
            return ResponseEntity.badRequest().build();
        }

        eventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestParam("date") String date,
                                  @RequestParam("time") String time,
                                  @RequestParam("location") String location,
                                  @RequestParam("description") String description) {

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateFormat = LocalDate.parse(date, formatter);

            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime timeFormat = LocalTime.parse(time, formatterTime);

            Event event = Event.builder()
                    .date(dateFormat)
                    .time(timeFormat)
                    .location(location)
                    .description(description)
                    .build();

            Event current = eventService.save(event);

            URI uri = URI.create(String.format("api/event/%d", current.getId()));
            return ResponseEntity.created(uri).body(current);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errors = new HashMap<>();

            errors.put("message", e.getMessage());
            errors.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.badRequest().body(errors);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("location") String location,
            @RequestParam("description") String description) {


        Map<String, Object> response = new HashMap<>();

        try {

            Event current = eventService.findById(id).orElse(null);

            if (Objects.isNull(current)) {
                throw new Exception("El evento no existe");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateFormat = LocalDate.parse(date, formatter);

            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime timeFormat = LocalTime.parse(time, formatterTime);

            current.setDate(dateFormat);
            current.setTime(timeFormat);
            current.setLocation(location);
            current.setDescription(description);

            eventService.edit(current);

            response.put("content", current);
            response.put("status", HttpStatus.OK.value());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errors = new HashMap<>();

            errors.put("message", e.getMessage());
            errors.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.badRequest().body(errors);
        }

    }

}