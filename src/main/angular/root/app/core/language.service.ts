import { Injectable } from "@angular/core";
import { TranslateService, LangChangeEvent } from "@ngx-translate/core";

@Injectable()
export class LanguageService {

  defaultLanguage = "fr";
  appLanguages = ["fr", "en"];
  appLanguage: string;

  constructor(private translateService: TranslateService) {
    this.translateService.addLangs(this.appLanguages);
    this.initLanguage();
  }

  private initLanguage() {
    this.translateService.setDefaultLang(this.defaultLanguage);
    this.appLanguage = this.defaultLanguage;
  }

  getAppLanguages(): Array<string> {
    return this.appLanguages;
  }

  getCurrentLanguage(): string {
    return this.appLanguage;
  }

  changeLanguage(language: string) {
    if (this.appLanguages.indexOf(language) > -1) {
      this.translateService.use(language);
    } else {
      console.log("Cannot change language to unknown '" + language + "'");
    }
  }

}
