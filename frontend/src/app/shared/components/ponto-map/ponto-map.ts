import {
  AfterViewInit,
  Component,
  OnChanges,
  OnDestroy,
  SimpleChanges,
  input,
  output,
} from '@angular/core';
import * as L from 'leaflet';
import { OPERACOES } from '../../../core/constants/operacoes';
import { Registro } from '../../../core/models/ponto.models';

@Component({
  selector: 'app-ponto-map',
  template: '<div id="map" aria-label="Mapa dos registros de ponto"></div>',
})
export class PontoMap implements AfterViewInit, OnChanges, OnDestroy {
  readonly registros = input<Registro[]>([]);
  readonly registroSelecionado = output<Registro>();

  private map?: L.Map;
  private readonly markers = L.layerGroup();
  private readonly markerById = new Map<string, L.Marker>();

  ngAfterViewInit(): void {
    this.map = L.map('map', {
      zoomControl: false,
      attributionControl: true,
    }).setView([-23.5616, -46.6562], 14);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; OpenStreetMap',
    }).addTo(this.map);
    L.control.zoom({ position: 'bottomright' }).addTo(this.map);
    this.markers.addTo(this.map);
    this.atualizarMarcadores(true);
  }

  ngOnChanges(_: SimpleChanges): void {
    if (this.map) this.atualizarMarcadores();
  }

  ngOnDestroy(): void {
    this.map?.remove();
  }

  focar(registro: Registro): void {
    const marker = this.markerById.get(registro.id);
    if (!this.map || !marker) return;
    this.map.flyTo(marker.getLatLng(), Math.max(this.map.getZoom(), 15), {
      duration: 0.7,
    });
    marker.openPopup();
  }

  usarMinhaLocalizacao(): void {
    if (!this.map || !navigator.geolocation) return;
    navigator.geolocation.getCurrentPosition(({ coords }) => {
      this.map?.flyTo([coords.latitude, coords.longitude], 16);
      L.circleMarker([coords.latitude, coords.longitude], {
        radius: 8,
        color: '#ffffff',
        weight: 3,
        fillColor: '#263c35',
        fillOpacity: 1,
      })
        .bindTooltip('Você está aqui')
        .addTo(this.map!);
    });
  }

  private atualizarMarcadores(ajustar = false): void {
    if (!this.map) return;
    this.markers.clearLayers();
    this.markerById.clear();
    const limites: L.LatLngExpression[] = [];

    for (const registro of this.registros()) {
      const info = OPERACOES[registro.operacao];
      const icon = L.divIcon({
        className: 'ponto-marker-wrapper',
        html: `<span class="ponto-marker" style="--marker-color:${info.cor}"><span></span></span>`,
        iconSize: [32, 42],
        iconAnchor: [16, 38],
        popupAnchor: [0, -34],
      });
      const marker = L.marker([registro.lat, registro.lng], { icon })
        .bindPopup(
          `<div class="map-popup"><strong>${this.escapar(registro.funcionarioNome)}</strong><span>${info.label}</span><small>${new Intl.DateTimeFormat('pt-BR', { dateStyle: 'short', timeStyle: 'short' }).format(new Date(registro.dataHoraOperacao))}</small></div>`,
        )
        .on('click', () => this.registroSelecionado.emit(registro))
        .addTo(this.markers);

      this.markerById.set(registro.id, marker);
      limites.push([registro.lat, registro.lng]);
    }

    if ((ajustar || limites.length > 0) && limites.length) {
      this.map.fitBounds(L.latLngBounds(limites).pad(0.22), {
        maxZoom: 15,
        animate: true,
      });
    }
  }

  private escapar(valor: string): string {
    const elemento = document.createElement('div');
    elemento.textContent = valor;
    return elemento.innerHTML;
  }
}
