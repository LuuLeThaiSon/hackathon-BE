import {AfterViewInit, Component, ElementRef} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'
  ]
})
export class AppComponent implements AfterViewInit{
  title = 'project';

  constructor(private elementRef:ElementRef) {
  }

  ngAfterViewInit() {
    var s = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../../assets/libs/bootstrap/js/bootstrap.bundle.min.js";
    this.elementRef.nativeElement.appendChild(s);

    var s1 = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../../assets/libs/simplebar/simplebar.min.js";
    this.elementRef.nativeElement.appendChild(s1);

    var s2 = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../../assets/libs/node-waves/waves.min.js";
    this.elementRef.nativeElement.appendChild(s2);

    var s3 = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../../assets/libs/glightbox/js/glightbox.min.js";
    this.elementRef.nativeElement.appendChild(s3);

    var s4 = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../../assets/libs/swiper/swiper-bundle.min.js";
    this.elementRef.nativeElement.appendChild(s4);

    var s5 = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../../assets/libs/fg-emoji-picker/fgEmojiPicker.js";
    this.elementRef.nativeElement.appendChild(s5);

    var s6 = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../../assets/js/pages/index.init.js";
    this.elementRef.nativeElement.appendChild(s6);

    var s7 = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../../assets/js/app.js";
    this.elementRef.nativeElement.appendChild(s7)
  }
}
