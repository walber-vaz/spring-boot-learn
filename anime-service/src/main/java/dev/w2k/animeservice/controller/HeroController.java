package dev.w2k.animeservice.controller;


import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/heroes")
public class HeroController {

  private static final List<String> HEROES = List.of("All Might", "Endeavor", "Hawks",
      "Best Jeanist", "Edgeshot");

  @GetMapping
  public List<String> listAllHeroes() {
    return HEROES;
  }

  @GetMapping("filter")
  public List<String> filterHeroes(@RequestParam(required = false) String name) {
    if (name == null) {
      return HEROES;
    }

    return HEROES.stream()
        .filter(hero -> hero.toLowerCase().contains(name.toLowerCase()))
        .toList();
  }

  @GetMapping("filterList")
  public List<String> filterParamList(@RequestParam(required = false) List<String> names) {
    return HEROES.stream()
        .filter(hero -> names.stream()
            .anyMatch(name -> hero.toLowerCase().contains(name.toLowerCase())))
        .toList();
  }

  @GetMapping("{name}")
  public String findByName(@PathVariable String name) {
    return HEROES.stream()
        .filter(hero -> hero.equalsIgnoreCase(name))
        .findFirst()
        .orElse("Hero not found");
  }
}
