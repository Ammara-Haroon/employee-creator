export interface Employee {
  id: number;
  firstName: string;
  middleName: string | null;
  lastName: string;
  email: string;
  mobileNumber: string;
  address: string;
  contractType: ContractType;
  startDate: Date;
  finishDate: Date | null;
  employmentType: EmploymentType;
  hoursPerWeek: number;
}

export enum EmploymentType {
  PART_TIME = "PART_TIME",
  FULL_TIME = "FULL_TIME",
}

export enum ContractType {
  PERMANENT = "PERMANENT",
  CONTRACT = "CONTRACT",
}
