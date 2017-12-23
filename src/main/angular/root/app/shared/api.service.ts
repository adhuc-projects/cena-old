import { Injectable } from "@angular/core";
import { environment } from "@env/environment.prod";
import "rxjs/Rx";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpHeaders } from "@angular/common/http";

export class Resource {
  private _links: any;

  constructor(resource: any) {
    this._links = resource._links;
  }

  getLink(rel: string): string {
    const ref = this._links[rel];
    return ref != null ? ref.href : null;
  }
}

@Injectable()
export class ApiService {
  apiResource: Resource;
  apiResourceObservable: Observable<Resource>;

  constructor(private http: HttpClient) {
    this.resetApiResource();
  }

  getApiIndex(): Observable<Resource> {
    return this.apiResourceObservable;
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
