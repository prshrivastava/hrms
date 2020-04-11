
export class Position {
    id: string;
    skills: string;
    experienceRange: string;
    hiringManager: string;
    priority: number;
    constructor(public name?: string) { this.id="0";}
}

export class PositionView extends Position {
    appliedCount: string;
    shortListCount: string;
    interviewingCount: string;
    resumeRejectedCount: string;
  }
