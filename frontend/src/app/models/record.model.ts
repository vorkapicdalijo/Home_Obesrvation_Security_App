export class Record {
  id: number;
  image: Uint8Array;
  imageDisplay: string;
  timestamp: string;

  constructor(id: number, image: Uint8Array, imageDisplay: string, timestamp: string) {
    this.id = id;
    this.image = image;
    this.imageDisplay = imageDisplay;
    this.timestamp = timestamp;
  }
}