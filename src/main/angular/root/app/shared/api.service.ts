import { Injectable } from "@angular/core";
import { environment } from "@env/environment";
import "rxjs/Rx";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpHeaders } from "@angular/common/http";

class ResourceLinkTransformer {

  private disabled: boolean;
  private original: string;
  private transformed: string;

  constructor() {
    this.disabled = environment.linkTransformer.disabled;
    if (!this.disabled) {
      this.original = environment.linkTransformer.original;
      this.transformed = environment.linkTransformer.transformed;
      this.disabled = this.original === this.transformed;
    }
  }

  transformLink(link: string): string {
    return (!this.disabled && link.startsWith(this.original)) ? link.replace(this.original, this.transformed) : link;
  }

}

export class Resource {
  private _links: any;
  private linkTransformer: ResourceLinkTransformer;

  constructor(resource: any) {
    this._links = resource._links;
    this.linkTransformer = new ResourceLinkTransformer();
  }

  getLink(rel: string): string {
    const ref = this._links[rel];
    return ref != null ? this.extractLink(ref) : null;
  }

  private extractLink(ref: any): string {
    const link: string = ref.href;
    return this.linkTransformer.transformLink(link);
  }

}

@Injectable()
export class ApiService {

  private readonly apiBaseUrl = "/api";

  public static readonly RECIPES_LINK_NAME = "recipes";
  public static readonly MENUS_LINK_NAME = "menus";
  public static readonly INGREDIENTS_MANAGEMENT_LINK_NAME = "ingredientsManagement";

  apiResource: Resource;
  apiResourceObservable: Observable<Resource>;

  constructor(private http: HttpClient) {
    this.resetApiResource();
  }

  getApiIndex(): Observable<Resource> {
    return this.apiResourceObservable;
  }

  resetApiResource() {
    this.apiResourceObservable = this.getApiResource();
    this.apiResourceObservable.subscribe(resource => {
      this.apiResource = resource;
      this.apiResourceObservable = Observable.of(resource);
    });
  }

  private getApiResource(): Observable<Resource> {
    return this.http.get(this.apiBaseUrl, {observe : "response"})
      .map(response => this.extractApiResource(response))
      .catch(error => Observable.of(new Resource({"_links": {}})));
  }

  private extractApiResource(response): Resource {
    return new Resource(response.body);
  }

}
