import { Component, OnInit } from "@angular/core";
import { LanguageService } from "@core/language.service";

@Component({
  selector: "cena-language-selection",
  templateUrl: "./language-selection.component.html",
  styleUrls: ["./language-selection.component.css"]
})
export class LanguageSelectionComponent implements OnInit {

  readonly languages: Array<string> = this.languageService.getAppLanguages();

  constructor(private languageService: LanguageService) { }

  ngOnInit() {
  }

  get currentLanguage() {
    return this.languageService.getCurrentLanguage();
  }

  changeCurrentLanguage(language) {
    this.languageService.changeLanguage(language);
  }

}
