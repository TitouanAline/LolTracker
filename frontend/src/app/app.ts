import { Component, signal } from '@angular/core';
import { SummonerComponent } from './features/summoner/summoner'

@Component({
  selector: 'app-root',
  imports: [SummonerComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('lol-web-app');
}
