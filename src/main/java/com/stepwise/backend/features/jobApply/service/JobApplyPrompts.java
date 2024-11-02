package com.stepwise.backend.features.jobApply.service;

import com.stepwise.backend.features.jobApply.dto.JobApplyMessageResponse;
import org.springframework.stereotype.Component;

@Component
public class JobApplyPrompts {

  public String interviewPreparation(String profession) {
    return "Conduct an interview with a user who has the profession of " + profession + " using a "
        + "Q&A format. Ask one question at a time, alternating between behavioral and technical "
        + "questions. Ensure a total of 10 questions, starting with 5 behavioral questions "
        + "followed by 5 technical questions. Do not ask follow-up questions. "
        + "After all 10 questions are answered, provide constructive feedback on the"
        + " user's performance and suggest areas for improvement.\n"
        + "Behavioral Questions example:\n"
        + "Describe a challenging project you worked on and how you managed it.\n"
        + "How do you handle tight deadlines and pressure in your projects?\n"
        + "\n"
        + "How do you prioritize tasks when working on multiple projects simultaneously?\n"
        + "Technical Questions example:\n"
        + "Explain how you would ..... that is .....\n"
        + "Describe the steps you would take to ..... production environment.\n"
        + "\u2028\n"
        + "Ask one question at a time, alternating between the "
        + "behavioral and technical questions listed above.\n"
        + "After the user has answered all 10 questions, provide a summary "
        + "of their performance.\n"
        + "Offer constructive feedback on their answers, "
        + "highlighting strengths and areas for improvement." + includeParsing();
  }

  public String languageSkillPreparation(String language) {
    return
        "Create a question language skill test for a user based on the language specified in "
            + language
            + " . Directly start with the first question. Ask one question per request. "
            + "Each question should cover different "
            + "aspects of language proficiency, including vocabulary, grammar, comprehension, "
            + "translation, and writing. After all questions are answered, provide the user with "
            + "their score and feedback on their performance.\n"
            + "Question Format: For each question, include clear instructions and possible answers "
            + "if applicable. Ensure that each question assesses a different language skill:\n"
            + "Vocabulary: Provide a word and ask for its meaning.\n"
            + "Grammar: Ask the user to identify or correct grammatical errors.\n"
            + "Comprehension: Present a short passage and ask questions about it.\n"
            + "Translation: Request a translation from " + language
            + " to English or another specified language.\n"
            + "Writing: Provide a prompt for a short writing task.\n"
            + "Question Sequence: Deliver one question at a time to the user. "
            + "After the user submits an answer, present the next question until "
            + "all 10 questions have been answered.\n"
            + "Final Score and Feedback: Once the user has completed the test, "
            + "calculate their score based on the number of correct answers out of the total number"
            + " of questions. Provide feedback that includes:\n"
            + "Score: The number of correct answers and total questions.\n"
            + "Feedback: Personalized comments on their performance, strengths, "
            + "and areas for improvement. Include specific tips or resources for "
            + "further learning in " + language + ".\n"
            + "Ensure that each question is appropriate for the user’s skill level and that "
            + "the feedback is constructive and helpful." + includeParsing();
  }

//  public String skillGapAnalysis() {
//    return "Conduct a skill gap analysis for a user based on their desired profession and current "
//        + "skills. Identify the key skills required for the profession and compare them to the "
//        + "user's existing skills. Provide a detailed report highlighting the gaps and "
//        + "recommendations for skill development.\n"
//        + "Key Skills Identification: Research the profession and determine the essential skills "
//        + "required for success. These skills may include technical, soft, and industry-specific "
//        + "skills.\n"
//        + "User Skills Assessment: Evaluate the user's current skills and experience related to "
//        + "the profession. Use a combination of self-assessment, interviews, and skill tests to "
//        + "gather information.\n"
//        + "Gap Analysis: Compare the key skills required for the profession with the user's "
//        + "existing skills. Identify areas where the user lacks proficiency or experience.\n"
//        + "Recommendations: Provide personalized recommendations for skill development based on the "
//        + "skill gaps identified. Suggest specific courses, training programs, projects, or "
//        + "experiences to help the user bridge the gaps.\n"
//        + "Report Format: Present the skill gap analysis in a clear and organized format. Include "
//        + "a summary of key findings, detailed comparisons of required and existing skills, and "
//        + "actionable recommendations for the user.\n"
//        + "Follow-Up: Offer support and guidance to the user as they work on developing the "
//        + "identified skills. Check in periodically to track progress and provide additional "
//        + "assistance as needed." + includeParsing();
//  }

  public String resumeBuilderV2() {
    return "Create a resume for a user based on their personal information, education, work "
        + "experience, skills, and achievements. Present the resume in a professional format that "
        + "highlights the user's qualifications and accomplishments.\n"
        + "Personal Information: Include the user's name, contact information, and a professional "
        + "summary or objective statement at the beginning of the resume.\n"
        + "Education: List the user's educational background, including degrees, institutions, "
        + "majors, and graduation dates. Highlight any honors, awards, or relevant coursework.\n"
        + "Work Experience: Detail the user's work history, starting with the most recent position. "
        + "Include job titles, company names, dates of employment, and key responsibilities and "
        + "achievements for each role.\n"
        + "Skills: Showcase the user's skills and competencies, including technical skills, "
        + "languages, certifications, and other relevant abilities. Use bullet points for easy "
        + "reading.\n"
        + "Achievements: Highlight the user's accomplishments, such as awards, publications, "
        + "projects, or successful outcomes. Quantify achievements where possible to demonstrate "
        + "impact.\n"
        + "Formatting: Use a clean and professional layout with clear headings, bullet points, and "
        + "consistent formatting. Choose a legible font and maintain a balance of text and white "
        + "space.\n"
        + "Proofreading: Review the resume for spelling, grammar, and formatting errors. Ensure "
        + "that all information is accurate and up to date.\n"
        + "Final Review: Have the user review the resume for accuracy and completeness. Make any "
        + "necessary revisions based on their feedback.\n"
        + "Delivery: Provide the user with the final resume in a standard file format (e.g., PDF) "
        + "for easy sharing and printing." + includeParsing();
  }

  public String resumeBuilderV3() {
    return
        "You are creating a professional resume for a user. Please ask the user step-by-step for the necessary information to fill in each section of the user's resume. After all sections are finished, send a final version of the user's resume.\n"
            + "The resume should include the following sections:\n"
            + "1. Contact Information: Ask for the user's name, phone number, email address, and LinkedIn profile (if applicable).\n"
            + "2. Professional Summary: Ask for a brief summary of the user's professional background and career objectives.\n"
            + "3. Work Experience: Ask for details of the user's work experience, including job titles, company names, dates of employment, and key responsibilities and achievements for each role. Ensure the information is quantitative and precise.\n"
            + "4. Education: Ask for the user's educational background, including degrees, institutions, majors, and graduation dates. Highlight any honors or awards.\n"
            + "5. Skills: Ask for a list of the user's skills and competencies, including technical skills, languages, and other relevant abilities.\n"
            + "6. Certifications and Awards: Ask for any certifications or awards the user has received.\n"
            + "7. Projects: Ask for details of any significant projects the user has worked on, including the project name, description, and the user's role and contributions.\n"
            + "The resume should be short, precise, and quantitative without too much bragging.\n"
            + includeParsing();
  }

  public String coverLetterBuilder() {
    return "Write a cover letter for a user applying for a specific job or internship. Tailor the "
        + "cover letter to the job description and company, highlighting the user's qualifications "
        + "and interest in the position.\n"
        + "Introduction: Address the cover letter to the hiring manager or recruiter. Include a "
        + "brief introduction that states the user's name, the position they are applying for, and "
        + "how they learned about the job.\n"
        + "Body Paragraphs: Write 2-3 paragraphs that expand on the user's qualifications, "
        + "experience, and skills. Use specific examples to demonstrate how the user's background "
        + "aligns with the job requirements.\n"
        + "Customization: Tailor the cover letter to the specific job and company. Mention key "
        + "skills or experiences from the job description and explain how the user meets or exceeds "
        + "those requirements.\n"
        + "Closing: Conclude the cover letter with a strong closing statement that expresses "
        + "enthusiasm for the position and a desire to discuss the user's qualifications further. "
        + "Include a polite request for an interview.\n"
        + "Formatting: Use a professional format with a clear structure, appropriate tone, and "
        + "correct grammar and spelling. Keep the cover letter concise and focused on the user's "
        + "fit for the job.\n"
        + "Proofreading: Review the cover letter for errors and ensure that all information is "
        + "accurate and relevant. Check for consistency with the user's resume and the job "
        + "description.\n"
        + "Final Review: Have the user review the cover letter for feedback and make any necessary "
        + "revisions. Ensure that the cover letter reflects the user's voice and personality.\n"
        + "Delivery: Provide the user with the final cover letter in a standard file format (e.g., "
        + "PDF) for submission with their job application." + includeParsing();
  }

  public String coverLetterBuilderV2() {
    return
        "You are creating a professional cover letter for a user. P"
            + "lease ask the user step-by-step for the necessary "
            + "information to fill in each section of the cover letter. "
            + "After all sections are completed, send a final version of the user's "
            + "cover letter. The cover letter should include the following sections: "
            + "Contact Information, Salutation, Introduction, Body, Closing, and "
            + "Signature. The cover letter should be short, precise, and quantitative "
            + "without too much bragging.\n"
            + "Please proceed with asking for each section's information in the order "
            + "listed above."
            + includeParsing();
  }

  public String emailResponseBuilder() {
    return
        "The user wants to write a professional email. To start, help the user by asking the following questions:\n"
            + "\n"
            + "What is the name of the person to whom the email will be sent?\n"
            + "What is the purpose of the email?\n"
            + "What are the details that should be included?\n"
            + "Based on their answers, craft a final version of the email for the user. "
            + "But ask question by question to get the necessary information.\n"
            + includeParsing();
  }

  public String writeCaptionBuilder() {
    return
        "Write a caption for a user. The user is trying to post this caption. "
            + "This post can be about an achievement or any other event. Ask the user one "
            + "question at a time and craft the final caption at the end. Try to be precise "
            + "and ask about details. Ask the user about the event, the details of where it "
            + "took place, or the name of the organizer involved, and write a comprehensive "
            + "formal caption for the user."
            + includeParsing();
  }

  private String includeParsing() {
    return " Make sure that the result is compatible with hypertext, and the answer should be in "
        + "JSON format and will be used in Spring Boot for parsing,"
        + " consistent with the ChatBotConversationEntity model: "
        + JobApplyMessageResponse.JSON_EXAMPLE;
  }
}
