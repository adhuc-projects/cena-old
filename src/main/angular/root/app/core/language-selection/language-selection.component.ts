import { Component, OnInit } from "@angular/core";
import { LanguageService } from "@core/language.service";

@Component({
  selector: "cena-language-selection",
  templateUrl: "./language-selection.component.html",
  styleUrls: ["./language-selection.component.css"]
})
export class LanguageSelectionComponent implements OnInit {

  readonly languages: Array<string> = this.languageService.getAppLanguages();
  currentLanguage: string;

  constructor(private languageService: LanguageService) { }

  ngOnInit() {
    this.currentLanguage = this.languageService.getCurrentLanguage();
  }

  changeCurrentLanguage(language) {
    this.languageService.changeLanguage(language);
  }

}
