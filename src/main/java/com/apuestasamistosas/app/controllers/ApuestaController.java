
package com.apuestasamistosas.app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apuestasamistosas.app.entities.Equipos;
import com.apuestasamistosas.app.entities.Eventos;
import com.apuestasamistosas.app.entities.Premio;
import com.apuestasamistosas.app.errors.ErrorApuesta;
import com.apuestasamistosas.app.errors.ErrorPremio;
import com.apuestasamistosas.app.services.ApuestaServicio;
import com.apuestasamistosas.app.services.EquiposServicio;
import com.apuestasamistosas.app.services.EventosServicio;
import com.apuestasamistosas.app.services.PremioServicio;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/bets")
@PreAuthorize("hasAnyRole('ROLE_USUARIO')")
@Slf4j
public class ApuestaController {
	
	@Autowired
	private ApuestaServicio apuestaServicio;
	
	@Autowired
	private PremioServicio premioServicio;
	
	@Autowired
	private EventosServicio eventoServicio;
	
	@Autowired 
	private EquiposServicio equipoServicio;
    
    @GetMapping
    public String index(){
        return "apuestas/index";
    }
    
	@GetMapping("/fromRewards/{id}")
	public String betFromRewards(@PathVariable String id, ModelMap model) throws ErrorApuesta {
		Optional<Premio> thisPremio = premioServicio.buscarPorId(id);

		if (thisPremio.isPresent()) {
			Premio premio = thisPremio.get();
			model.addAttribute("premio", premio);
			model.addAttribute("listaEventos", eventoServicio.eventosOrdenadosPorFechaYSinExpirar());
			return "apuestas/crear-apuesta";
		} else {
			return "redirect:/rewards";
		}

	}
	
	@GetMapping("/beginBet")
	public String createBet(
			@RequestParam(name = "idReward", required = false) String idReward,
			@RequestParam(name = "idEvent", required = false) String idEvent,
			@RequestParam(name = "idTeam", required = false) String idTeam, ModelMap model) throws ErrorApuesta{
		
		Optional<Premio> thisPremio = premioServicio.buscarPorId(idReward);
		Optional<Eventos> thisEvento = eventoServicio.buscarPorId(idEvent);
		Optional<Equipos> thisEquipo = equipoServicio.buscarPorId(idTeam);
		
		if(thisPremio.isPresent() && thisEvento.isPresent() && thisEquipo.isPresent()) {
			try {
				apuestaServicio.llamarPrimerValidacion(idReward, idEvent, idTeam);
				model.addAttribute("premio", thisPremio.get());
				model.addAttribute("evento", thisEvento.get());
				model.addAttribute("equipo", thisEquipo.get());
				return "apuestas/checkout-apuesta";
			}catch(ErrorApuesta e) {
				return "redirect:/error";
			}
		}else {
			return "redirect:/error";
		}
	}
	
	@PostMapping("/payBet")
	public String checkoutBet(
			@RequestParam(name = "idReward", required = false) String idReward,
			@RequestParam(name = "idEvent", required = false) String idEvent,
			@RequestParam(name = "idTeam", required = false) String idTeam,
			@RequestParam(name = "idUser", required = false) String idUser,
			ModelMap model) throws ErrorApuesta {
		
		 try {
			 apuestaServicio.crearApuesta(idUser, idEvent, idReward, idTeam);
			 model.addAttribute("message", "??Apuesta creada exitosamente!");
			 return "apuestas/post-checkout";
		 }catch(ErrorApuesta e) {
			 model.addAttribute("error", e.getMessage());
			 return "apuestas/post-checkout";
		 }
	}
	
	
	
  

}
