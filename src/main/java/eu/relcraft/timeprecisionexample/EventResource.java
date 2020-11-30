package eu.relcraft.timeprecisionexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Controller
class EventResource {
    private EventService eventService;

    @Value("${spring.datasource.url}")
    private String springDatasourceUrl;

    @Autowired
    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    String home(Model model) {
        LocalDate testedDay = LocalDate.of(2020, Month.NOVEMBER, 12);

        model.addAttribute("springDatasourceUrl", springDatasourceUrl);
        model.addAttribute("events", eventService.findAll());
        model.addAttribute("testedDay", testedDay);
        model.addAttribute("evenCountBadVersion", eventService.findEventCountIfStartIsLessOrEqualBadVersion(testedDay));
        model.addAttribute("evenCount", eventService.findEventCountIfStartIsLessOrEqual(testedDay));

        return "index";
    }
}