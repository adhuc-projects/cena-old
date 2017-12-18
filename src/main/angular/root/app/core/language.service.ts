import { Injectable } from "@angular/core";
import { TranslateService, LangChangeEvent } from "@ngx-translate/core";
import { CookieService } from "ngx-cookie-service";

@Injectable()
export class LanguageService {

  private readonly COOKIE_LANGUAGE = "lang";

  defaultLanguage = "fr";
  appLanguages = ["fr", "en"];
  appLanguage: string;

  constructor(private translateService: TranslateService, private cookieService: CookieService) {
    this.translateService.addLangs(this.appLanguages);
    this.initLanguage();
  }

  private initLanguage() {
    this.translateService.setDefaultLang(this.defaultLanguage);

    const langFromCookie = this.cookieService.get(this.COOKIE_LANGUAGE);
    this.setCurrentLanguage(langFromCookie ? langFromCookie : this.defaultLanguage);
  }

  getAppLanguages(): Array<string> {
    return this.appLanguages;
  }

  getCurrentLanguage(): string {
    return this.appLanguage;
  }

  changeLanguage(language: string) {
    if (this.appLanguages.indexOf(language) > -1) {
      this.setCurrentLanguage(language);
    } else {
      console.log("Cannot change language to unknown '" + language + "'");
    }
  }

  private setCurrentLanguage(language: string) {
    console.log("Set current language to " + language);
    this.cookieService.set(this.COOKIE_LANGUAGE, language);
    this.appLanguage = language;
    this.translateService.use(language);
  }

}
