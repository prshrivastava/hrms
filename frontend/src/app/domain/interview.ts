import { Candidate } from './candidate';

export class Interview {
    id: string;
    slot: Date;
    panel: string;
    state: string;
}

export class InterviewingCandidate extends Candidate {
    interviews: Interview[];
}

export class InterviewFeedback {
    interview: Interview;
    feedback: [Feedback];
}

export class Feedback {
    submittedBy: string;
    submittedOn: Date;
    comments: string;
}