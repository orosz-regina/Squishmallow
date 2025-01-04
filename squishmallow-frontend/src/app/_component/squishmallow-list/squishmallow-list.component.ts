import { Component, OnInit } from '@angular/core';
import { SquishmallowService } from '../../_service/squishmallow.service';
import {CommonModule, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-squishmallow-list',
  templateUrl: './squishmallow-list.component.html',
  imports: [
    NgIf,
    NgForOf
  ],
  styleUrls: ['./squishmallow-list.component.css']
})
export class SquishmallowListComponent implements OnInit {

  squishmallows: any[] = [];

  constructor(private squishmallowService: SquishmallowService) { }

  ngOnInit(): void {
    this.squishmallowService.getSquishmallows().subscribe(data => {
      this.squishmallows = data;
    });
  }
}
