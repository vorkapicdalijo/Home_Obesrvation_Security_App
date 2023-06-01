export class Record {
  id: number;
  image: any;
  imageDisplay: string;
  timestamp: string;
  sanitizedImage: any;

  constructor(id: number, image: any, imageDisplay: string, timestamp: string, sanitizedImage: any) {
    this.id = id;
    this.image = image;
    this.imageDisplay = imageDisplay;
    this.timestamp = timestamp;
    this.sanitizedImage = sanitizedImage;
  }
}