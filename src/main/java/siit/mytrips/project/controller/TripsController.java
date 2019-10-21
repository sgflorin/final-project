package siit.mytrips.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import siit.mytrips.project.model.Trip;
import siit.mytrips.project.persistence.TripRepository;


import java.util.*;

@Controller
public class TripsController {

    @Autowired
    private TripRepository tripRepository;

    @RequestMapping("/addtrip")
    public String addTrip(Model model) {
        Trip trip = new Trip();
        model.addAttribute("trip", trip);
        return "addtrip";
    }


    @RequestMapping(name = "/confirmedTrip", method = RequestMethod.POST)
    public ModelAndView saveTrip(@ModelAttribute Trip trip) {

        ModelAndView model = new ModelAndView("confirmedTemplate");

        if (trip != null && trip.getId() != null) {
            Optional<Trip> personalOpt = tripRepository.findById(trip.getId());

            if (personalOpt.isPresent()) {
                Trip persisted = personalOpt.get();
                persisted.setName(trip.getName());
                persisted.setStartDate(trip.getStartDate());
                persisted.setEndDate(trip.getEndDate());
                persisted.setLocation(trip.getLocation());
                persisted.setImpression(trip.getImpression());
                persisted.setDetails1(trip.getDetails1());
                persisted.setDetails2(trip.getDetails2());

                tripRepository.save(persisted);
            }

            Trip persisted = personalOpt.get();
            if (persisted != null) {
                persisted.setName(trip.getName());
                persisted.setStartDate(trip.getStartDate());
                persisted.setEndDate(trip.getEndDate());
                persisted.setLocation(trip.getLocation());
                persisted.setImpression(trip.getImpression());
                persisted.setDetails1(trip.getDetails1());
                persisted.setDetails2(trip.getDetails2());
                tripRepository.save(persisted);
            }
        }
        Trip saved = tripRepository.save(trip);

        model.addObject("tripSaved", saved.getName() + " --- with ID: " + saved.getId());
        return model;
    }


    @RequestMapping(value = "/mytrips", method = RequestMethod.GET)
    public ModelAndView getTrips(@ModelAttribute Trip t,@RequestParam(name = "id", required = false) Long id) {

        Iterable <Trip> mytrips = tripRepository.findAll();

        Trip currentTrip = null;
        if(id != null) {
            for(Trip trip: mytrips) {
                if (trip.getId().equals(id)) {
                    currentTrip = trip;
                    System.out.println("Found:" + trip);
                    break;
                }
            }
        }
        if(currentTrip == null) {
            currentTrip=mytrips.iterator().next();
        }

        ModelAndView model = new ModelAndView("mytrips");
        model.addObject("mytrips", mytrips);
        model.addObject("selectedId", currentTrip.getId());
        model.addObject("currentTrip", currentTrip);
        return model;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteTrip(@ModelAttribute Trip t,@RequestParam(name = "id", required = false) Long id) {
        tripRepository.deleteById(id);
        return "deleteRedirect";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editTripFill(Model model,@RequestParam(name = "id", required = false) Long id) {
        Trip trip = new Trip();
        Optional <Trip> tripOptional = tripRepository.findById(id);

        if (tripOptional.isPresent()){
            trip = tripOptional.get();
            model.addAttribute("trip", trip);
        }
        return "edit";
    }

    @RequestMapping(name = "/editTrip", method = RequestMethod.GET)
    public ModelAndView editComplete(@ModelAttribute Trip tripReq,@RequestParam(name = "id", required = false) Long id) {

        ModelAndView model = new ModelAndView("editRedirect");

        Optional <Trip> tripOptional = tripRepository.findById(id);

        if(tripOptional.isPresent()) {

            Trip tripDb = tripOptional.get();
            if (tripDb.getId().equals(id)){
            tripDb.setId(id);
            tripDb.setName(tripReq.getName());
            tripDb.setImpression(tripReq.getImpression());
            tripDb.setLocation(tripReq.getLocation());
            tripDb.setStartDate(tripReq.getStartDate());
            tripDb.setEndDate(tripReq.getEndDate());
            tripDb.setDetails1(tripReq.getDetails1());
            tripDb.setDetails2(tripReq.getDetails2());
            tripRepository.save(tripDb);
            }
        }
        return model;
    }
}
