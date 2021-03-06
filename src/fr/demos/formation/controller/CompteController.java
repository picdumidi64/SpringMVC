package fr.demos.formation.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.LocaleResolver;

import fr.demos.formation.data.CompteDAO;
import fr.demos.formation.model.Adresse;
import fr.demos.formation.model.Compte;
import fr.demos.formation.model.ComptePremium;

@Controller
@SessionAttributes("compte")
public class CompteController {

	@Autowired
	private CompteDAO comptedao;

	public CompteDAO getComptedao() {
		return comptedao;
	}

	public void setComptedao(CompteDAO comptedao) {
		this.comptedao = comptedao;
	}

	@Autowired
	private LocaleResolver sessionLocaleResolver;

	@RequestMapping(value = "/saisieCompte.htm", method = RequestMethod.GET)
	public String affichePage(ModelMap model) {

		model.addAttribute("compte", new Compte());
		return "saisieCompte";

	}

	@RequestMapping(value = "/rechercherCompteParId.htm", method = RequestMethod.POST)
	public String rechercheCompte(ModelMap model, @RequestParam(name = "email") String email) {

		// appel du find dans dao

		Compte compte = comptedao.find(email);
		// r�cup du compte, on le stocke dans le model
		if (compte != null) {
			model.addAttribute("compte", compte);
		} else {
			model.addAttribute("compte", new Compte());
		}
		return "saisieCompte";
	}

	@RequestMapping(value = "/english.htm", method = RequestMethod.GET)
	public String english(HttpServletRequest request, HttpServletResponse response) {

		sessionLocaleResolver.setLocale(request, response, Locale.ENGLISH);
		return "saisieCompte";

	}

	@RequestMapping(value = "/essaiHibernate.htm", method = RequestMethod.GET)
	public String essaiHibernate(ModelMap model) {

		Adresse a1 = new Adresse("Adresse de facturation", 22, "Rue de la Paix", 75000, "Paris");

		Adresse a2 = new Adresse("Adresse de livraison", 31, "Rue des Maronniers", 75012, "Paris");

		List<Adresse> lad = new ArrayList<Adresse>();
		lad.add(a1);
		lad.add(a2);

		Compte compte1 = new Compte("Toto", "Titi", LocalDate.of(2012, 12, 12), "toto.titi@hotmail.com", lad);

		try {
			comptedao.insert(compte1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "essaiHibernate";
	}

	@RequestMapping(value = "/essaiHeritage.htm", method = RequestMethod.GET)
	public String essaiHeritage(ModelMap model) {

		Adresse a1 = new Adresse("Adresse de facturation", 22, "Rue de la Paix", 75000, "Paris");

		Adresse a2 = new Adresse("Adresse de livraison", 31, "Rue des Maronniers", 75012, "Paris");

		List<Adresse> lad = new ArrayList<Adresse>();
		lad.add(a1);
		lad.add(a2);

		Compte compte1 = new Compte("Allo", "ici", LocalDate.of(2012, 12, 12), "ici.allo@hotmail.com", lad);

		List<Compte> lstf = new ArrayList<Compte>();

		lstf.add(compte1);

		ComptePremium cp = new ComptePremium("Hassel", "David", LocalDate.of(2007, 10, 03), "david.hassel@hotmail.com",
				lad, 10, 10, LocalDate.of(2016, 03, 12), lstf);

		try {
			comptedao.insert(cp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "essaiHeritage";
	}

	@RequestMapping(value = "/french.htm", method = RequestMethod.GET)
	public String french(HttpServletRequest request, HttpServletResponse response) {

		sessionLocaleResolver.setLocale(request, response, Locale.FRENCH);
		return "saisieCompte";

	}

	@RequestMapping(value = "/afficheCompte.htm", method = RequestMethod.GET)
	public String afficheCompte(ModelMap model) {

		model.addAttribute("mesComptes", comptedao.select());
		return "afficheCompte";

	}

	@RequestMapping(value = "/enregistrerCompte.htm", method = RequestMethod.POST)
	public String enregistrerCompte(@ModelAttribute("compte") @Valid Compte compte, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {


		try {

			comptedao.insert(compte);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (result.hasErrors()) {

			return "saisieCompte";

		} else {

			return "saisieSuccess";
		}

	}
	
	

	@RequestMapping(value = "/affichePageAdresse.htm", method = RequestMethod.POST)
	public String affichePageAdresse(ModelMap model) {

		model.addAttribute("adresse", new Adresse());
		return "affichePageAdresse";

	}

	@RequestMapping(value = "/enregistrerCompteAvecAdresse.htm", method = RequestMethod.POST)
	public String enregistrerCompteAvecAdresse(@ModelAttribute("compte") @Valid Compte compte, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		try {

			comptedao.insert(compte);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (result.hasErrors()) {

			return "saisieCompte";

		} else {

			return "saisieAdresse";
		}

	}

}
