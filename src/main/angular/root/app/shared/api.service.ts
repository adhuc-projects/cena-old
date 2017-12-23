import { Injectable } from '@angular/core';
import { environment } from '@env/environment.prod';
import "rxjs/Rx";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpHeaders } from "@angular/common/http";

class Resource {
  _links: Link;

  constructor(links: any) {
    this._links = Object.assign(new Link(), links._links);
  }

  getLink(rel: string): string {
    return this._links.getLink(rel);
  }
}

class Link {
  documentation: LinkRef;
  management: LinkRef;
  ingredients: LinkRef;
  recipes: LinkRef;
  menus: LinkRef;
  ingredientsManagement: LinkRef;

  getLink(rel: string): string {
    const ref = Object.getOwnPropertyDescriptor(this, rel);
    return ref != null ? ref.value.href : null;
  }
}

class LinkRef {
  href: string;
}

@Injectable()
export class ApiService {
  apiResource: Resource;
  apiResourceObservable: Observable<Resource>;

  constructor(private http: HttpClient) {
    this.resetApiResource();
  }

  getLink(rel: string): Observable<string> {
    return this.apiResourceObservable.map(resource => resource.getLink(rel));
  }

  resetApiResource() {
    this.apiResourceObservable = this.getApiResource();
    this.apiResourceObservable.subscribe(resource => {
      this.apiResource = resource;
      this.apiResourceObservable = Observable.of(resource);
    });
  }

  private getApiResource(): Observable<Resource> {
    return this.http.get(environment.apiUrl, {observe : "response"})
      .map(response => this.extractApiResource(response))
      .catch(error => Observable.of(new Resource(new Object())));
  }

  private extractApiResource(response): Resource {
    return new Resource(response.body);
  }

}
