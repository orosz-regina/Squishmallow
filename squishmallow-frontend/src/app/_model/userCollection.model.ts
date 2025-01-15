export class Squishmallow {
  id!: number;
  name!: string;
  type!: string;
  size!: string;
  category!: string;
}

export class Collection {
  id!: number;
  squishmallowId!: number;
  addedAt!: Date;
  squishmallow!: Squishmallow;
}
